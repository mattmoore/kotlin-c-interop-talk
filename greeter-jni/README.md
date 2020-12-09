# Bridging The Shared Library to Kotlin JVM

First, you'll want to read up on [A Primer on Native Libraries](../greeter). In that section you'll build a `libgreeter.dylib` shared library that we'll need before continuing this section.

In order to make Kotlin on the JVM call our shared library, we'll have to create another C library using JNI to wrap around the C library we just created.

## Step 1: JNI Header File [include/greeter_jni.h](include/greeter_jni.h)

Let's create another header file called [include/greeter_jni.h](include/greeter_jni.h):

```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <jni.h>

const char * hello(const char *);

JNIEXPORT jstring JNICALL Java_io_mattmoore_kotlin_playground_cinterop_Greeter_hello(JNIEnv *, jobject, jstring);
```

Note that I've imported `jni.h`. This is the JNI header file required to translate the function calls between Java and C over the JNI spec. Part of the spec requires proper naming. The name of the function being exposed over JNI has to follow a specific naming convention. You'll notice the portion `Java_io_mattmoore_kotlin_playground_cinterop_Greeter_hello` - JNI recognizes the `Java_` portion of the name as a marker to expose this function. The part after that (`io_mattmoore...`) matches the full package name of the class in Kotlin that will expose the exported native function.

## Step 2: JNI Implementation File [src/greeter_jni.c](src/greeter_jni.c)

Now inspect the [src/greeter_jni.c](src/greeter_jni.c) implementation file:

```c
#include "greeter_jni.h"

JNIEXPORT jstring JNICALL Java_io_mattmoore_kotlin_playground_cinterop_Greeter_hello(JNIEnv *env, jobject obj, jstring name) {
    const char * nameBuffer = (*env)->GetStringUTFChars(env, name, NULL);
    const char * helloBuffer = hello(nameBuffer);
    (*env)->ReleaseStringUTFChars(env, name, nameBuffer);
    jstring greeting = (*env)->NewStringUTF(env, helloBuffer);
    return greeting;
}
```

Wow! There's a lot going on here! `greeter_jni.c` implements the translation between the JVM and the pure C `libgreeter.dylib` library we created initially.

Compile it with `cmake` ([CMakeLists.txt](CMakeLists.txt)):

```shell
mkdir build
cd build
cmake ..
make
```

This new shared library [build/libgreeter_jni.dylib](build/libgreeter_jni.dylib) is similarly named after the shared library `libgreeter.dylib` we created earlier, but with the suffix `_jni`. You don't have to follow this naming convention, but it's a good idea to keep it clear which library is the C implementation and which one is the JNI bridge.

## Step 3: Kotlin C-Interop Class

Now we can create our Kotlin class to load the `libgreeter.dylib` and `libgreeter_jni.dylib` libraries and call the `hello` function. There is a `Greeter.kt` file:

```kotlin
class Greeter {
  init {
    System.loadLibrary("greeter")
    System.loadLibrary("greeter_jni")
  }

  external fun hello(name: String): String
}
```

The first key part here is `System.loadLibrary` where we pass in the name of the JNI library `libgreeter_jni.dylib` we just created, as well as the original `libgreeter.dylib` C library. Note that the string name we pass in should exclude the `lib` and `.dylib` portions of the shared library, as `System.loadLibrary` automatically adds those in.

The next important part is the external `hello` function we're declaring. By putting the `external` keyword in front of the function, Kotlin will look up the implementation of the function from within the shared library.

# Bridging The Shared Library to Kotlin JVM

First, you'll want to read up on [A Primer on Native Libraries](../libhello). In that section you'll build a `libhello.dylib` shared library that we'll need before continuing this section.

In order to make Kotlin on the JVM call our shared library, we'll have to create another C library using JNI to wrap around the C library we just created. Let's create another header file called `hello_jni.h`:

```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <jni.h>

JNIEXPORT jstring JNICALL Java_Hello_hello(JNIEnv *env, jobject obj, jstring name);
```

Create the `hello.c` implementation file:

```c
#include "hello.h"

JNIEXPORT jstring JNICALL Java_Hello_hello(JNIEnv *env, jobject obj, jstring name) {
  const char * nameBuffer = (*env)->GetStringUTFChars(env, name, NULL);
  char * helloBuffer = (char *)malloc(0);
  sprintf(helloBuffer, "Hello, %s.", nameBuffer);
  (*env)->ReleaseStringUTFChars(env, name, nameBuffer);
  jstring hello = (*env)->NewStringUTF(env, helloBuffer);
  free(helloBuffer);
  return hello;
}
```

Compile it:

```shell
gcc \
  -o libhello_jni.dylib \
  -shared -fPIC \
  -I/Library/Java/JavaVirtualMachines/adoptopenjdk-12.jdk/Contents/Home/include \
  -I/Library/Java/JavaVirtualMachines/adoptopenjdk-12.jdk/Contents/Home/include/darwin \
  -L. \
  -lhello \
  hello_jni.c
```

Wow! There's a lot going on here! So, first we use the `-o libhello_jni.dylib` command to specify the shared library name. Notice the name of this new shared library `libhello_jni.dylib` is very similar to the shared library `libhello.dylib` we created earlier. This new "JNI" library will act as a translation layer between the JVM and the pure C `libhello.dylib` library we created initially. This is unfortunately necessary for the JVM to translate between C and Java structures.

Next, we're specifying header files with the `-I` options. Those are the paths containing the `jni.h` and `jni_md.h` files that we need to include in order for the JNI code in our `hello_jni.c` file to work correctly.

Finally, we specify the `-L.` option to specify the current directory where our original `libhello.dylib` exists, and the `-lhello` option specifies the name of the original `libhello.dylib` file.

This will create a `libhello_jni.dylib` shared library that will translate the calls from the JVM to our C code, and back again.

## Create Kotlin C-Interop Class

Now we can create our Kotlin class to load the `libhello_jni.dylib` library and call its `hello` function. Create a `Hello.kt` file:

```kotlin
class Hello {
  init {
    System.loadLibrary("hello_jni")
  }
  external fun hello(name: String): String
}
```

The first key part here is `System.loadLibrary` where we pass in the name of the JNI library we just created. Note that the string name we pass in should exclude the `lib` and `.dylib` portions of the shared library, as `System.loadLibrary` automatically adds those in.

The next important part is the external `hello` function we're declaring. By putting the `external` keyword in front of the function, Kotlin will look up the implementation of the function from within the shared library.

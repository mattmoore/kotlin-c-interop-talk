# Kotlin C-Interop for JVM and Native

Kotlin is a great language! But what if you want to call an existing native library in Kotlin, or build your own native library for performance optimizations? This is also useful for making Kotlin a viable option for systems programming. Perhaps there’s an existing C or C++ library that would be great to have available in Kotlin, but it isn’t yet Kotlin-ready? We’ll take a look at how to access native libraries with Kotlin.

There are two paradigms to consider when using Kotlin to interact with native code: Kotlin on the JVM and Kotlin Native. While the heaviest use of Kotlin is in the world of the Java Virtual Machine, there is an increasing interest and set of use-cases for Kotlin Native. JetBrains has clearly shown they are pursuing Kotlin native fairly aggressively.


## A Primer on Native Libraries

Lots of existing native code has been created and optimized for use on various operating systems. The way this native code is shared is by compiling the native code into what is called a "shared library". On Linux, this is a file that has a `.so` extension; on Macos it is named with a `.dylib` extension. For example, if we were going to create a simple hello world native library in C called "hello" that we wanted to share, we could write our C function and compile it to a file named `libhello.so` on Linux and `libhello.dylib` on Macos. Whether creating our own C/C++ shared library or using an existing one built by someone else, we can take this shared library and bridge it over to other languages using a technique called C-Interop. Kotlin is no exception.

Kotlin has multiplatform support. Here we'll only discuss Kotlin JVM and Kotlin Native, though there are other platforms. When bridging a native shared library over to Kotlin JVM vs Native, we have to handle the process a little differently. As a side note, bridging native libraries to Kotlin Native is considerably easier than with Kotlin JVM.

To begin with, let's build an example hello library in C that we can then share with Kotlin JVM or Kotlin Native. We'll need to do the following:

1. Create a C header file called `hello.h`.
1. Create a C implementation file called `hello.c`.
1. Compile these files using GCC (GNU C Compiler).

### Step 1. hello.h:

In the C language, a header file contains a definition of the function signatures. A function signature is the name of the function, the parameters it accepts, and the data type it returns.

```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char * hello(const char * name);
```

### Step 2: hello.c:

Next, we create our hello.c file. This is called an "implementation file". This defines the details that go inside our function.

```c
#include "hello.h"

char * hello(const char * name) {
  char * helloBuffer = (char *)malloc(0);
  sprintf(helloBuffer, "Hello, %s.", name);
  return helloBuffer;
}
```

#### Why Header Files?

You might be wondering why we need header files, given that it appears we're defining the function signature in both `hello.h` as well as `hello.c`. Indeed, most other languages do not have header files! In the C and C++ world, a header file acts as an early form of reflection for telling other programs (as well as humans) that want to link to the shared library what functions exist inside of it, at a glance. More modern languages have reflection built-in, making header files unnecessary!

### Step 3: Compile The Shared Library

Finally, let's run our compiler to create the shared library. Note this is for Macos:

```shell
gcc -o libhello.dylib -shared -fPIC hello.c
```

If you want to compile the shared library for linux, we just have to change the part `-o libhello.dylib` to `-o libhello.so`:

```shell
gcc -o libhello.so -shared -fPIC hello.c
```

This will convert the `hello.c` file into a file named `libhello.dylib`, which is the final shared library. Notice we don't have to specify the `hello.h` file in the command; this is because `gcc` already knows about it through the `#include "hello.h"` statement on the first line of `hello.c`. Once this is done, our shared library is ready to share!

#### Test Our Library!

Before we get into the details of bridging the library to Kotlin, let's test that our shared library works natively within C, just to make sure we've built it correctly.

Let's make another file called `main.c`:

```c
#include <stdio.h>
#include "hello.h"

int main(int argc, char *argv[]) {
  printf("%s\n", hello(argv[1]));
}
```

Then compile `main.c`:

```shell
gcc -o hello -I. -L. -lhello main.c
```

This creates an executable file called `hello`. We can run it now:

```shell
./hello Matt
Hello, Matt.
```

Let's talk about the compiler options as they'll be important to understand when we bridge libraries with Kotlin.

`-I.` This tells the gcc compiler where our `hello.h` file is located. It has a `.` after the `-I`, which points to the current directory.

`-L.` Specifies the location where the compiled `libhello.dylib` file exists (or `libhello.so` on Linux). As with the `-I` option, it's in the current directory, so we put a `.`.

`-lhello` This tells the compiler the name of our library. In this case, it is called `hello`. Notice this doesn't match the full name of `libhello.dylib`. Whenever referencing a shared library name, we omit the `lib` and `.dylib` parts of the name. So in this case, `libhello.dylib` becomes `hello`.

## Bridging The Shared Library to Kotlin JVM

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
gcc -o libhello_jni.dylib -shared -fPIC -I/Library/Java/JavaVirtualMachines/adoptopenjdk-12.jdk/Contents/Home/include -I/Library/Java/JavaVirtualMachines/adoptopenjdk-12.jdk/Contents/Home/include/darwin -L. -lhello hello_jni.c
```

Wow! There's a lot going on here! So, first we use the `-o libhello_jni.dylib` command to specify the shared library name. Notice that name of this new shared library `libhello_jni.dylib` is very similar to the shared library `libhello.dylib` we created earlier. This new "JNI" library will act as a translation layer between the JVM and the pure C `libhello.dylib` library we created initially. This is unfortunately necessary for the JVM to translate between C and Java structures.

Next, we're specifying header files with the `-I` options. Those are the paths containing the `jni.h` and `jni_md.h` files that we need to include in order for the JNI code in our `hello_jni.c` file to work correctly.

Finally, we specify the `-L.` option to specify the current directory where our original `libhello.dylib` exists, and the `-lhello` option specifies the name of the original `libhello.dylib` file.

This will create a `libhello_jni.dylib` shared library that will translate the calls from the JVM to our C code, and back again.

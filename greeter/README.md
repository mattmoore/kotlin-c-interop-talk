# A Primer on Native Libraries

Lots of existing native code has been created and optimized for use on various operating systems. The way this native code is shared is by compiling the native code into what is called a "shared library". On Linux, this is a file that has a `.so` extension; on Macos it is named with a `.dylib` extension. For example, if we were going to create a simple hello world native library in C called "libgreeter" that we wanted to share, we could write our C function and compile it to a file named `libgreeter.so` on Linux and `libgreeter.dylib` on Macos. Whether creating our own C/C++ shared library or using an existing one built by someone else, we can take this shared library and bridge it over to other languages using a technique called C-Interop. Kotlin is no exception.

Kotlin has multiplatform support. Here we'll only discuss Kotlin JVM and Kotlin Native, though there are other platforms. When bridging a native shared library over to Kotlin JVM vs Native, we have to handle the process a little differently. As a side note, bridging native libraries to Kotlin Native is considerably easier than with Kotlin JVM.

To begin with, let's build an example greeter library in C that we can then share with Kotlin JVM or Kotlin Native. We'll need to do the following:

1. Create a C header file called `greeter.h`.
1. Create a C implementation file called `greeter.c`.
1. Compile these files using GCC (GNU C Compiler).
1. Test our library out by using it in an executable program.

## Step 1: `greeter.h`

In the C language, a header file contains a definition of the function signatures. A function signature is the name of the function, the parameters it accepts, and the data type it returns.

```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char * hello(const char * name);
```

## Step 2: `greeter.c`

Next, we create our greeter.c file. This is called an "implementation file". This defines the details that go inside our function.

```c
#include "greeter.h"

char * hello(const char * name) {
  char * helloBuffer = (char *)malloc(0);
  sprintf(helloBuffer, "Hello, %s.", name);
  return helloBuffer;
}
```

### Why Header Files?

You might be wondering why we need header files, given that it appears we're defining the function signature in both `greeter.h` as well as `greeter.c`. Indeed, most other languages do not have header files! In the C and C++ world, a header file acts as an early form of reflection for telling other programs (as well as humans) that want to link to the shared library what functions exist inside of it, at a glance. More modern languages have reflection built-in, making header files unnecessary!

## Step 3: Compile The Shared Library

Finally, let's run our compiler to create the shared library. Note this is for Macos:

```shell
gcc -o libgreeter.dylib -shared -fPIC greeter.c
```

If you want to compile the shared library for linux, we just have to change the part `-o libgreeter.dylib` to `-o libgreeter.so`:

```shell
gcc -o libgreeter.so -shared -fPIC greeter.c
```

This will convert the `greeter.c` file into a file named `libgreeter.dylib`, which is the final shared library. Notice we don't have to specify the `greeter.h` file in the command; this is because `gcc` already knows about it through the `#include "greeter.h"` statement on the first line of `greeter.c`. Once this is done, our shared library is ready to share!

## Step 4: Test Our Library!

Before we get into the details of bridging the library to Kotlin, let's test that our shared library works natively within C, just to make sure we've built it correctly.

Let's make another file called `main.c`:

```c
#include <stdio.h>
#include "greeter.h"

int main(int argc, char *argv[]) {
  printf("%s\n", greeter(argv[1]));
}
```

Then compile `main.c`:

```shell
gcc -o greeter -I. -L. -lgreeter main.c
```

This creates an executable file called `greeter`. We can run it now:

```shell
./greet Matt
Hello, Matt.
```

Let's talk about the compiler options as they'll be important to understand when we bridge libraries with Kotlin.

`-I.` This tells the gcc compiler where our `greeter.h` file is located. It has a `.` after the `-I`, which points to the current directory.

`-L.` Specifies the location where the compiled `libgreeter.dylib` file exists (or `libgreeter.so` on Linux). As with the `-I` option, it's in the current directory, so we put a `.`.

`-lgreeter` This tells the compiler the name of our library. In this case, it is called `greeter`. Notice this doesn't match the full name of `libgreeter.dylib`. Whenever referencing a shared library name, we omit the `lib` and `.dylib` parts of the name. So in this case, `libgreeter.dylib` becomes `greeter`.

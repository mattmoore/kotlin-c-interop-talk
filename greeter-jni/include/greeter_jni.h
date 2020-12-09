#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <jni.h>

const char * hello(const char *);

JNIEXPORT jstring JNICALL Java_io_mattmoore_kotlin_playground_cinterop_Greeter_hello(JNIEnv *, jobject, jstring);

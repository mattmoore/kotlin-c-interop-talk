#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <jni.h>

JNIEXPORT jstring JNICALL Java_Hello_hello(JNIEnv *env, jobject obj, jstring name);

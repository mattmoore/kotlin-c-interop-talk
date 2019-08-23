#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <jni.h>
#include "greeter.h"

JNIEXPORT jstring JNICALL Java_Greeter_hello(JNIEnv *env, jobject obj, jstring name);

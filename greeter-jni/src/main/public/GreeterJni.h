#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <jni.h>
#include "Greeter.h"

JNIEXPORT jstring JNICALL Java_Greeter_hello(JNIEnv*, jobject, jstring);

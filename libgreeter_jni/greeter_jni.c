#include "greeter_jni.h"
#include "greeter.h"

JNIEXPORT jstring JNICALL Java_Greeter_hello(JNIEnv *env, jobject obj, jstring name) {
  const char * nameBuffer = (*env)->GetStringUTFChars(env, name, NULL);

  const char * helloBuffer = hello(nameBuffer);

  (*env)->ReleaseStringUTFChars(env, name, nameBuffer);
  jstring greeting = (*env)->NewStringUTF(env, helloBuffer);

  return greeting;
}

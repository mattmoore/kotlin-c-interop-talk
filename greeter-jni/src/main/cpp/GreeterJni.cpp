#include "GreeterJni.h"

JNIEXPORT jstring JNICALL Java_Greeter_hello(JNIEnv* env, jobject obj, jstring name) {
  const char * nameBuffer = env->GetStringUTFChars(name, NULL);

  const char * helloBuffer = hello(nameBuffer);

  env->ReleaseStringUTFChars(name, nameBuffer);
  jstring greeting = env->NewStringUTF(helloBuffer);

  return greeting;
}

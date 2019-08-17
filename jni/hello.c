#include "hello.h"

JNIEXPORT jstring JNICALL Java_Hello_hello(JNIEnv *env, jobject obj, jstring name) {
  // Convert the incoming jstring to C-style string.
  const char * nameBuffer = (*env)->GetStringUTFChars(env, name, NULL);

  // Create a C buffer for formatting the greeting.
  char * helloBuffer = (char *)malloc(0);
  // Generate the greeting.
  sprintf(helloBuffer, "Hello, %s.", nameBuffer);
  // Release the jstring-to-C buffer as soon as we're done with it to prevent leaks.
  (*env)->ReleaseStringUTFChars(env, name, nameBuffer);

  // Convert the C-style string to a jstring so we can return it.
  jstring hello = (*env)->NewStringUTF(env, helloBuffer);

  // Free memory for the buffer to prevent leaks.
  free(helloBuffer);

  // Finally, return the jstring greeting.
  return hello;
}

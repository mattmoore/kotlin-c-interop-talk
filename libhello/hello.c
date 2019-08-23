#include "hello.h"

char * hello(const char * name) {
  char * helloBuffer = (char *)malloc(0);
  sprintf(helloBuffer, "Hello, %s.", name);
  return helloBuffer;
}

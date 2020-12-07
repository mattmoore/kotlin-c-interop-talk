#include "Greeter.h"

const char * hello(const char * name) {
  char * helloBuffer;
  sprintf(helloBuffer, "Hello, %s.", name);
  return helloBuffer;
}
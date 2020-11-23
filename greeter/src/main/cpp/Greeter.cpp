#include "Greeter.h"

const char * hello(const char * name) {
  char * helloBuffer;
  sprintf(helloBuffer, "Hello, %s.", name);
  return helloBuffer;
}

//char * hello(const char * name) {
//  char * helloBuffer = (char *)malloc(0);
//  sprintf(helloBuffer, "Hello, %s.", name);
//  return helloBuffer;
//}

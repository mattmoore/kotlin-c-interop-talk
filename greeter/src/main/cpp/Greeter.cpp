#include "Greeter.h"

extern "C" const char * hello(const char * name) {
  return std::string("Hello, " + std::string(name) + ".").c_str();
}

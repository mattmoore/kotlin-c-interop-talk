#include <stdio.h>

const char * hello(const char *);

int main(int argc, char *argv[]) {
  printf("%s\n", hello(argv[1]));
}

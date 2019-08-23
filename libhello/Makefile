build: shared static

static: libhello-static
shared: libhello-shared

prepare:
	@if [ ! -d build ]; then mkdir build; fi

libhello-static: prepare
	@echo "Building libhello static library."
	@gcc -c hello.c -o build/libhello.o
	@ar -rc build/libhello.a build/libhello.o

libhello-shared: prepare
	@echo "Building libhello shared library."
	@gcc -o build/libhello.dylib -shared -fPIC hello.c

clean:
	@rm -rf build
	@echo "Cleaned."


# Installation

install: install-headers install-shared install-static
	@echo "Finished installing libhello."

install-headers:
	@echo "Installing headers."
	@cp hello.h /usr/local/include

install-shared: install-headers libhello-shared
	@echo "Installing libhello shared."
	@cp build/libhello.dylib /usr/local/lib

install-static: install-headers libhello-static
	@echo "Installing libhello static."
	@cp build/libhello.a /usr/local/lib

uninstall:
	@echo "Uninstalling libhello."
	rm -f /usr/local/include/hello.h
	rm -f /usr/local/lib/libhello.dylib
	rm -f /usr/local/lib/libhello.a

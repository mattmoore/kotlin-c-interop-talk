build: shared static

static: libgreeter-static
shared: libgreeter-shared

prepare:
	@if [ ! -d build ]; then mkdir build; fi

libgreeter-static: prepare
	@echo "Building libgreeter static library."
	@gcc -c greeter.c -o build/libgreeter.o
	@ar -rc build/libgreeter.a build/libgreeter.o

libgreeter-shared: prepare
	@echo "Building libgreeter shared library."
	@gcc -o build/libgreeter.dylib -shared -fPIC greeter.c

sign:
	@codesign -f -s 2LX8Z7X9NZ --timestamp -v build/libgreeter.dylib

clean:
	@rm -rf build
	@echo "Cleaned."


# Installation

install: install-headers install-shared install-static
	@echo "Finished installing libgreeter."

install-headers:
	@echo "Installing headers."
	@cp greeter.h /usr/local/include

install-shared: install-headers libgreeter-shared
	@echo "Installing libgreeter shared."
	@cp build/libgreeter.dylib /usr/local/lib

install-static: install-headers libgreeter-static
	@echo "Installing libgreeter static."
	@cp build/libgreeter.a /usr/local/lib

uninstall:
	@echo "Uninstalling libgreeter."
	rm -f /usr/local/include/greeter.h
	rm -f /usr/local/lib/libgreeter.dylib
	rm -f /usr/local/lib/libgreeter.a

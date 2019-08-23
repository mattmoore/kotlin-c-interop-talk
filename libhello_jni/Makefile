build: libhello_jni-shared

prepare:
	@if [ ! -d build ]; then mkdir build; fi

libhello_jni-shared: prepare
	@echo "Building libhello_jni shared library."
	@gcc \
		-o build/libhello_jni.dylib \
		-shared -fPIC \
		-I/Library/Java/JavaVirtualMachines/openjdk-12.0.1.jdk/Contents/Home/include \
		-I/Library/Java/JavaVirtualMachines/openjdk-12.0.1.jdk/Contents/Home/include/darwin \
		-lhello \
		hello_jni.c

clean:
	@rm -rf build
	@echo "Cleaned."


# Installation

install: install-headers install-shared
	@echo "Finished installing libhello_jni."

install-headers:
	@echo "Installing headers."
	@cp hello_jni.h /usr/local/include

install-shared: install-headers libhello_jni-shared
	@echo "Installing libhello_jni shared."
	@cp build/libhello_jni.dylib /usr/local/lib

uninstall:
	@echo "Uninstalling libhello_jni."
	rm -f /usr/local/include/hello_jni.h
	rm -f /usr/local/lib/libhello_jni.dylib

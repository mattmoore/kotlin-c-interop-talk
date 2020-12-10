build: libgreeter_jni-shared

prepare:
	@if [ ! -d build ]; then mkdir build; fi

libgreeter_jni-shared: prepare
	@echo "Building libgreeter_jni shared library."
	@gcc \
		-o build/libgreeter_jni.dylib \
		-shared -fPIC \
		-I/Library/Java/JavaVirtualMachines/adoptopenjdk-15.jdk/Contents/Home/include \
		-I/Library/Java/JavaVirtualMachines/adoptopenjdk-15.jdk/Contents/Home/include/darwin \
		-I/Users/mattmoore/source/kotlin-cinterop-talk/libgreeter \
		-L/Users/mattmoore/source/kotlin-cinterop-talk/libgreeter/build \
		-lgreeter \
		greeter_jni.c

sign:
	@codesign -f -s 2LX8Z7X9NZ --timestamp -v build/libgreeter_jni.dylib

clean:
	@rm -rf build
	@echo "Cleaned."


# Installation

install: install-headers install-shared
	@echo "Finished installing libgreeter_jni."

install-headers:
	@echo "Installing headers."
	@cp greeter_jni.h /usr/local/include

install-shared: install-headers libgreeter_jni-shared
	@echo "Installing libgreeter_jni shared."
	@cp build/libgreeter_jni.dylib /usr/local/lib

uninstall:
	@echo "Uninstalling libgreeter_jni."
	rm -f /usr/local/include/greeter_jni.h
	rm -f /usr/local/lib/libgreeter_jni.dylib

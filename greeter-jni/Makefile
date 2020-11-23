build: libgreeter_jni-shared

prepare:
	@if [ ! -d build ]; then mkdir build; fi

libgreeter_jni-shared: prepare
	@echo "Building libgreeter_jni shared library."
	@gcc \
		-o build/libgreeter_jni.dylib \
		-shared -fPIC \
		-I/Library/Java/JavaVirtualMachines/openjdk-12.0.1.jdk/Contents/Home/include \
		-I/Library/Java/JavaVirtualMachines/openjdk-12.0.1.jdk/Contents/Home/include/darwin \
		-lgreeter \
		greeter_jni.c

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

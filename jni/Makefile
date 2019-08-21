build: shared

shared: libhello-shared kotlin
static: libhello-static kotlin

prepare:
	@if [ ! -d build ]; then mkdir build; fi

libhello-shared: prepare
	@echo "Building C code."
	@gcc \
		-o build/libhello.dylib \
		-shared -fPIC \
		-I/Library/Java/JavaVirtualMachines/openjdk-12.0.1.jdk/Contents/Home/include \
		-I/Library/Java/JavaVirtualMachines/openjdk-12.0.1.jdk/Contents/Home/include/darwin \
		hello.c

libhello-static: prepare
	@echo "Building C code."
	@gcc \
		-o build/libhello.o \
		-I/Library/Java/JavaVirtualMachines/openjdk-12.0.1.jdk/Contents/Home/include \
		-I/Library/Java/JavaVirtualMachines/openjdk-12.0.1.jdk/Contents/Home/include/darwin \
		-c hello.c
	@ar -rc build/libhello.a build/libhello.o

kotlin: prepare
	@echo "Building Kotlin code."
	@kotlinc-jvm -include-runtime -d build/Hello.jar *.kt

clean:
	@rm -rf build
	@echo "Cleaned."

run:
	@java -jar -Djava.library.path=build build/Hello.jar

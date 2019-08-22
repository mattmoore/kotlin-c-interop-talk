build: shared

static: libhello-static cinterop-static kotlin-static

shared: libhello-shared cinterop-shared kotlin-shared

prepare:
	@if [ ! -d build ]; then mkdir build; fi

libhello-static: prepare
	@echo "Building C code."
	@gcc -c hello.c -o build/libhello.o
	@ar -rc build/libhello.a build/libhello.o

libhello-shared: prepare
	@echo "Building C code."
	@gcc -o build/libhello.dylib -shared -fPIC hello.c

hellotest: prepare
	@echo "Building hello c main test."
	@gcc -o build/program *.c

cinterop-static: prepare
	@echo "Building cinterop."
	@cinterop -def libhello-static.def -o build/libhello.klib

cinterop-shared: prepare
	@echo "Building cinterop."
	@cinterop -def libhello-shared.def -o build/libhello.klib

kotlin-static: prepare
	@echo "Building Kotlin code."
	@kotlinc-native -o build/program -l build/libhello.klib *.kt

kotlin-shared: prepare
	@echo "Building Kotlin code."
	@kotlinc-native \
		-o build/program \
		-l build/libhello.klib \
		-linker-options build/libhello.dylib \
		*.kt

clean:
	@rm -rf build
	@echo "Cleaned."

run:
	@build/program.kexe

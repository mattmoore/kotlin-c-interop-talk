all: libhello cinterop kotlin

prepare:
	@if [ ! -d build ]; then mkdir build; fi

libhello: prepare
	@echo "Building C code."
	@gcc \
		-o build/libhello.dylib \
		-shared -fPIC \
		hello.c

hellotest: prepare
	@echo "Building hello c main test."
	@gcc \
		-o build/program \
		*.c

cinterop: prepare
	@echo "Building cinterop."
	@cinterop \
		-def libhello.def \
		-o build/libhello

kotlin: prepare
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

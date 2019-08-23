build: shared

shared: cinterop kotlin-shared
static: cinterop kotlin-static

prepare:
	@if [ ! -d build ]; then mkdir build; fi

cinterop: prepare
	@echo "Building cinterop."
	@cinterop -def libhello.def -o build/libhello.klib

kotlin-shared: prepare
	@echo "Building Kotlin code."
	@kotlinc-native \
		-o build/program \
		-l build/libhello.klib \
		-linker-options /usr/local/lib/libhello.dylib \
		*.kt

kotlin-static: prepare
	@echo "Building Kotlin code."
	@kotlinc-native -o build/program -l build/libhello.klib *.kt

clean:
	@rm -rf build
	@echo "Cleaned."

run:
	@build/program.kexe

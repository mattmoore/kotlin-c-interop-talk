build: shared

shared: cinterop kotlin-shared
static: cinterop kotlin-static

prepare:
	@if [ ! -d build ]; then mkdir build; fi

cinterop: prepare
	@echo "Building cinterop."
	@cinterop -def libgreeter.def -o build/libgreeter.klib

kotlin-shared: prepare
	@echo "Building Kotlin code."
	@kotlinc-native \
		-o build/hello \
		-l build/libgreeter.klib \
		-linker-options /usr/local/lib/libgreeter.dylib \
		*.kt

kotlin-static: prepare
	@echo "Building Kotlin code."
	@kotlinc-native -o build/hello -l build/libgreeter.klib *.kt

clean:
	@rm -rf build
	@echo "Cleaned."

run:
	@build/hello.kexe ${ARGS}

build: kotlin

prepare:
	@if [ ! -d build ]; then mkdir build; fi

kotlin: prepare
	@echo "Building Kotlin code."
	@kotlinc-jvm -include-runtime -d build/Greeter.jar *.kt

clean:
	@rm -rf build
	@echo "Cleaned."

run:
	java -jar -Djava.library.path="/usr/local/lib:/Users/mattmoore/source/kotlin-cinterop-talk/libgreeter/build:/Users/mattmoore/source/kotlin-cinterop-talk/libgreeter_jni/build" build/Greeter.jar ${ARGS}

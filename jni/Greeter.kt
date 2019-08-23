class Greeter {
  init {
    System.loadLibrary("greeter_jni")
  }

  external fun hello(name: String): String
}

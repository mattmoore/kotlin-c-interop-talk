class Greeter {
  init {
    System.loadLibrary("greeter-jni")
  }

  external fun hello(name: String): String
}

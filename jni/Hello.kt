class Hello {
  init {
    System.loadLibrary("hello_jni")
  }

  external fun hello(name: String): String
}

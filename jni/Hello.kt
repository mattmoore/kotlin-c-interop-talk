class Hello {
  init {
    System.loadLibrary("hello")
  }
  external fun hello(name: String): String
}

package io.mattmoore.kotlin.playground.cinterop

class Greeter {
  init {
    System.loadLibrary("greeter-jni")
  }

  external fun hello(name: String): String
}

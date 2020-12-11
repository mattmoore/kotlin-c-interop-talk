# Kotlin C-Interop for JVM and Native

Kotlin is a great language! But what if you want to call an existing native library in Kotlin, or build your own native library for performance optimizations? This is also useful for making Kotlin a viable option for systems programming. Perhaps there’s an existing C or C++ library that would be great to have available in Kotlin, but it isn’t yet Kotlin-ready? We’ll take a look at how to access native libraries with Kotlin.

There are two paradigms to consider when using Kotlin to interact with native code: Kotlin on the JVM and Kotlin Native. While the heaviest use of Kotlin is in the world of the Java Virtual Machine, there is an increasing interest and set of use-cases for Kotlin Native. JetBrains has clearly shown they are pursuing Kotlin native fairly aggressively.

## [A Primer on Native Libraries](greeter)

## [Bridging The Shared Library to Kotlin JVM](greeter-jni)

## [Call Native Library from Kotlin Native](native-app)
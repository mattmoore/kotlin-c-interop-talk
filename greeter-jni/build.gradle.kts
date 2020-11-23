plugins {
  `cpp-library`
}

library {
  dependencies {
    implementation(project(":greeter"))
  }
  binaries.configureEach(CppSharedLibrary::class.java) {
    compileTask.get().compilerArgs.addAll(
      "-std=c++2a", "-fdeclspec",
      "-I/Library/Java/JavaVirtualMachines/adoptopenjdk-15.jdk/Contents/Home/include",
      "-I/Library/Java/JavaVirtualMachines/adoptopenjdk-15.jdk/Contents/Home/include/darwin"
    )
  }
  linkage.set(listOf(Linkage.STATIC, Linkage.SHARED))
  targetMachines.set(listOf(machines.macOS.x86_64))
}

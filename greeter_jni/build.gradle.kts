plugins {
  `cpp-library`
}

library {
  binaries.configureEach(CppStaticLibrary::class.java) {
    compileTask.get().compilerArgs.add("-std=c++2a")
  }
  binaries.configureEach(CppSharedLibrary::class.java) {
    compileTask.get().compilerArgs.add("-std=c++2a")
    compileTask.get().compilerArgs.add("-I${project(":greeter").projectDir}/include")
    compileTask.get().compilerArgs.add("-I/Library/Java/JavaVirtualMachines/adoptopenjdk-15.jdk/Contents/Home/include")
    compileTask.get().compilerArgs.add("-I/Library/Java/JavaVirtualMachines/adoptopenjdk-15.jdk/Contents/Home/include/darwin")
    compileTask.get().compilerArgs.add("-lgreeter")
  }
  source.from(file("src"))
  privateHeaders.from(file("src"))
  publicHeaders.from(file("include"))
  linkage.set(listOf(Linkage.STATIC, Linkage.SHARED))
  targetMachines.set(listOf(machines.macOS.x86_64))
}
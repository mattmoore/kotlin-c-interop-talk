plugins {
  `cpp-library`
}

library {
  binaries.configureEach(CppStaticLibrary::class.java) {
    compileTask.get().compilerArgs.add("-std=c++2a")
    compileTask.get().compilerArgs.add("-fdeclspec")
  }
  binaries.configureEach(CppSharedLibrary::class.java) {
    compileTask.get().compilerArgs.add("-std=c++2a")
    compileTask.get().compilerArgs.add("-fdeclspec")
  }
  linkage.set(listOf(Linkage.STATIC, Linkage.SHARED))
  targetMachines.set(listOf(machines.macOS.x86_64))
}
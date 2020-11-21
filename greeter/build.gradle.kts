plugins {
  `cpp-library`
}

library {
  binaries.configureEach(CppStaticLibrary::class.java) {
    compileTask.get().compilerArgs.add("-std=c++2a")
  }
  binaries.configureEach(CppSharedLibrary::class.java) {
    compileTask.get().compilerArgs.add("-std=c++2a")
  }
  source.from(file("src"))
  privateHeaders.from(file("src"))
  publicHeaders.from(file("include"))
  linkage.set(listOf(Linkage.STATIC, Linkage.SHARED))
  targetMachines.set(listOf(machines.macOS.x86_64))
}
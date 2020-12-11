plugins {
  kotlin("multiplatform")
}

repositories {
  jcenter()
  mavenCentral()
}

dependencies {
  commonMainImplementation(files("greeter-jni/build/libgreeter.dylib"))
  commonMainImplementation(kotlin("stdlib-jdk8"))
}

kotlin {
  jvm()
  macosX64 {
    val main by compilations.getting
    val interop by main.cinterops.creating

    compilations["main"].cinterops.create("greeter-jni") {
      val javaHome = File(System.getProperty("java.home")!!)
      packageName = "io.mattmoore.kotlin.playground.cinterop"
      includeDirs(
        Callable { File(javaHome, "include") },
        Callable { File(javaHome, "include/darwin") },
        Callable { File("${project.rootDir}/greeter-jni/include") }
      )
    }
  }

  linuxX64() {
    val main by compilations.getting
    val interop by main.cinterops.creating

    compilations["main"].cinterops.create("greeter-jni") {
      val javaHome = File(System.getProperty("java.home")!!)
      packageName = "io.mattmoore.kotlin.playground.cinterop"
      includeDirs(
        Callable { File(javaHome, "include") },
        Callable { File(javaHome, "include/linux") },
        Callable { File("${project.rootDir}/greeter-jni/include") }
      )
    }
  }
}

tasks.withType<Wrapper> {
  gradleVersion = "6.7.1"
  distributionType = Wrapper.DistributionType.ALL
}

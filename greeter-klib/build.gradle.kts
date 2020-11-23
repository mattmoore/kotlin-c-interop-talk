plugins {
  kotlin("multiplatform")
}

repositories {
  jcenter()
  mavenCentral()
}

dependencies {
  project(":greeter-jni")
  commonMainImplementation(kotlin("stdlib-jdk8"))
}

kotlin {
  jvm()
  macosX64 {
    val main by compilations.getting
    val interop by main.cinterops.creating

    binaries {
      executable()
    }
  }
}

tasks.withType<Wrapper> {
  gradleVersion = "6.7.1"
  distributionType = Wrapper.DistributionType.ALL
}

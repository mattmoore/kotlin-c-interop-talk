plugins {
  kotlin("multiplatform") version "1.4.10"
}

repositories {
  jcenter()
  mavenCentral()
}

dependencies {
  project(":greeter-jni")
}

kotlin {
  macosX64("native") {
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
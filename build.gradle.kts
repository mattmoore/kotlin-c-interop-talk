//import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("multiplatform") version "1.4.20"
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
//val compileKotlin: KotlinCompile by tasks
//compileKotlin.kotlinOptions {
//  jvmTarget = "1.8"
//}
//val compileTestKotlin: KotlinCompile by tasks
//compileTestKotlin.kotlinOptions {
//  jvmTarget = "1.8"
//}
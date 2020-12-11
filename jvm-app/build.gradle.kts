plugins {
  id("org.jetbrains.kotlin.jvm")
  id("com.github.johnrengelman.shadow") version "5.0.0"
  application
}

repositories {
  jcenter()
  mavenCentral()
}

dependencies {
  implementation(project(":greeter-klib"))
  implementation(kotlin("stdlib"))
}

application {
  mainClassName = "io.mattmoore.kotlin.playground.cinterop.MainKt"
}

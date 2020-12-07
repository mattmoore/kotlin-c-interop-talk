plugins {
  id("org.jetbrains.kotlin.jvm") version "1.4.20"
  id("com.github.johnrengelman.shadow") version "5.0.0"
  application
}

repositories {
  jcenter()
  mavenCentral()
}

dependencies {
  implementation(project(":greeter-klib"))
}

application {
  mainClassName = "io.mattmoore.kotlin.playground.cinterop.MainKt"
}

tasks.withType<Wrapper> {
  gradleVersion = "6.7.1"
  distributionType = Wrapper.DistributionType.ALL
}

plugins {
  id("org.jetbrains.kotlin.jvm") version "1.4.20"
}

repositories {
  jcenter()
  mavenCentral()
}

dependencies {
  implementation(project(":greeter-klib"))
  implementation(project(":jvm-app"))
}

tasks.withType<Wrapper> {
  gradleVersion = "6.7.1"
  distributionType = Wrapper.DistributionType.ALL
}

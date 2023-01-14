import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.6.21"
  kotlin("plugin.spring") version "1.6.21"
}

dependencies {
  implementation(project(":board-core"))

  //web
  implementation("org.springframework.boot:spring-boot-starter-web")

  //mysql
  runtimeOnly("mysql:mysql-connector-java")

  //kotlin
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "17"
  }
}

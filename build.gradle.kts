plugins {
  kotlin("jvm") version "1.9.25"
  kotlin("plugin.spring") version "1.9.25"
  id("org.springframework.boot") version "3.4.1"
  id("io.spring.dependency-management") version "1.1.7"
  kotlin("plugin.jpa") version "1.8.22" // Apply the JPA plugin
  kotlin("kapt") version "1.8.22"
  id("org.jetbrains.kotlin.plugin.noarg") version "1.9.25" // Apply the no-arg plugin
}

group = "trile"
version = "0.0.1-SNAPSHOT"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

repositories {
  mavenCentral()
}

extra["springModulithVersion"] = "1.3.1"

configurations.forEach { it.exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging") }

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("io.github.wimdeblauwe:htmx-spring-boot:4.0.1")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.springframework.modulith:spring-modulith-starter-core")
  developmentOnly("org.springframework.boot:spring-boot-devtools")
//    developmentOnly("org.springframework.boot:spring-boot-docker-compose")

  implementation("org.springframework.boot:spring-boot-starter-log4j2")
  implementation("org.apache.logging.log4j:log4j-api-kotlin:1.5.0")

  implementation("org.springframework.boot:spring-boot-starter-data-jpa")

//  runtimeOnly("com.h2database:h2")
  runtimeOnly("com.mysql:mysql-connector-j")

  runtimeOnly("org.springframework.modulith:spring-modulith-actuator")
  runtimeOnly("org.springframework.modulith:spring-modulith-observability")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.boot:spring-boot-testcontainers")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
  testImplementation("org.springframework.modulith:spring-modulith-starter-test")
  testImplementation("org.testcontainers:junit-jupiter")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
  testImplementation("io.mockk:mockk:1.13.7")
}

dependencyManagement {
  imports {
    mavenBom("org.springframework.modulith:spring-modulith-bom:${property("springModulithVersion")}")
  }
}

noArg {
  annotation("jakarta.persistence.Entity") // Add the @Entity annotation
  annotation("jakarta.persistence.MappedSuperclass")
  annotation("jakarta.persistence.Embeddable")
}

kotlin {
  compilerOptions {
    freeCompilerArgs.addAll("-Xjsr305=strict")
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}

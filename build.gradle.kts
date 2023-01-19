import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    kotlin("jvm") version "1.8.0"
    kotlin("plugin.spring") version "1.8.0" apply false
    id("org.springframework.boot") version "3.0.1" apply false
    id("io.spring.dependency-management") version "1.1.0" apply false
}

allprojects {
    apply {
        plugin("idea")
        plugin("jacoco")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("io.spring.dependency-management")
    }

    repositories {
        mavenCentral()
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "17"
            }
        }

        withType<Test> {
            useJUnitPlatform()
        }
    }
}

subprojects {
    dependencies {
        testImplementation("io.kotest:kotest-runner-junit5:5.5.4")
        testImplementation("io.github.serpro69:kotlin-faker:1.13.0")
        testImplementation("com.tngtech.archunit:archunit:1.0.1")
        testImplementation("com.tngtech.archunit:archunit-junit5:1.0.1")
    }
}

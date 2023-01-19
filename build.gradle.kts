import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.unbrokendome.gradle.plugins.testsets.dsl.testSets

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
    id("org.unbroken-dome.test-sets") version "4.0.0"
}

allprojects {
    apply {
        plugin("idea")
        plugin("jacoco")
        plugin("org.jetbrains.kotlin.jvm")
    }

    repositories {
        mavenCentral()
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "18"
            }
        }

        withType<Test> {
            useJUnitPlatform()
        }
    }
}

subprojects {
    apply {
        plugin("io.spring.dependency-management")
        plugin("org.unbroken-dome.test-sets")
    }

    dependencies {
        testImplementation("io.kotest:kotest-runner-junit5:5.5.4")
        testImplementation("io.kotest:kotest-assertions-core:5.5.4")
        testImplementation("io.kotest:kotest-property:5.5.4")
        testImplementation("io.github.serpro69:kotlin-faker:1.13.0")
        testImplementation("com.tngtech.archunit:archunit:1.0.1")
        testImplementation("com.tngtech.archunit:archunit-junit5:1.0.1")
    }
}

configure(subprojects) {
    testSets {
        val archTest by creating
    }
}

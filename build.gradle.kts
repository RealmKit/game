/*
 * Copyright (c) 2023 RealmKit
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.unbrokendome.gradle.plugins.testsets.dsl.testSets

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    jacoco
    kotlin("jvm") version "1.8.0"
    kotlin("plugin.spring") version "1.8.0" apply false
    id("org.sonarqube") version "3.5.0.2730"
    id("org.springframework.boot") version "3.0.2" apply false
    id("org.springframework.boot") version "3.0.1" apply false
    id("io.spring.dependency-management") version "1.1.0" apply false
    id("org.unbroken-dome.test-sets") version "4.0.0"
    id("io.gitlab.arturbosch.detekt") version "1.22.0"
}

allprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.sonarqube")
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
    }
}

subprojects {
    apply {
        plugin("idea")
        plugin("jacoco")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.unbroken-dome.test-sets")
        plugin("io.gitlab.arturbosch.detekt")
    }

    dependencies {
        detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.22.0")
        detektPlugins("io.gitlab.arturbosch.detekt:detekt-rules-ruleauthors:1.22.0")
        detektPlugins("com.braisgabin.detekt:kotlin-compiler-wrapper:0.0.2")

        testImplementation("io.kotest:kotest-runner-junit5:5.5.4")
        testImplementation("io.kotest:kotest-assertions-core:5.5.4")
        testImplementation("io.kotest:kotest-property:5.5.4")
        testImplementation("io.github.serpro69:kotlin-faker:1.13.0")
        testImplementation("com.tngtech.archunit:archunit:1.0.1")
        testImplementation("com.tngtech.archunit:archunit-junit5:1.0.1")
    }

    testSets {
        create("archTest")
        create("itest")
    }

    detekt {
        buildUponDefaultConfig = true
        allRules = true
    }

    tasks {
        withType<Test> {
            useJUnitPlatform()
            finalizedBy("detekt", "jacocoTestReport")
        }

        withType<Detekt>().configureEach {
            jvmTarget = "18"
            reports {
                html.required.set(true)
                xml.required.set(true)
                txt.required.set(true)
                sarif.required.set(true)
                md.required.set(true)
            }
        }

        jacocoTestReport {
            dependsOn(allprojects.map { it.tasks.withType<Test>() })
            reports {
                html.required.set(true)
                html.outputLocation.set(File("$buildDir/reports/jacoco/report.html"))
                xml.required.set(true)
                xml.outputLocation.set(File("$buildDir/reports/jacoco/report.xml"))
            }
        }
    }

    sonarqube {
        properties {
            property("sonar.jacoco.reportPaths", "$buildDir/reports/jacoco/report.exec")
        }
    }

    val bootJar: BootJar by tasks
    bootJar.enabled = false
}

tasks {
    register<JacocoReport>("coverage") {
        group = "coverage"
        description = "Test Coverage"
        dependsOn(allprojects.map { it.tasks.withType<Test>() })
        executionData(fileTree(project.rootDir.absolutePath).include("**/build/jacoco/*.exec"))
        sourceSets(project.extensions.getByType(SourceSetContainer::class.java).getByName("main"))
        reports {
            html.required.set(true)
            html.outputLocation.set(File("$buildDir/reports/jacoco/report.html"))
            xml.required.set(true)
            xml.outputLocation.set(File("$buildDir/reports/jacoco/report.xml"))
        }
        finalizedBy("coverageMerge")
    }

    register<JacocoMerge>("coverageMerge") {
        group = "coverage"
        description = "Test Coverage Aggregator"
        dependsOn("coverage")
        destinationFile = file("$buildDir/reports/jacoco/report.exec")
        executionData = project.fileTree(".") {
            include("**/*.exec")
            exclude("**/report.exec")
        }
    }
}

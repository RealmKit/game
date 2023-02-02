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

import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessPlugin
import io.spring.gradle.dependencymanagement.DependencyManagementPlugin
import org.jetbrains.dokka.gradle.DokkaPlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.sonarqube.gradle.SonarQubePlugin
import org.springframework.boot.gradle.plugin.SpringBootPlugin
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.unbrokendome.gradle.plugins.testsets.TestSetsPlugin
import org.unbrokendome.gradle.plugins.testsets.dsl.testSets
import java.util.Calendar.YEAR

buildscript {
    repositories {
        mavenCentral()
    }
}

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    jacoco
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.test.sets)
    alias(libs.plugins.dokka)
    alias(libs.plugins.spotless)
    alias(libs.plugins.sonarqube)
    alias(libs.plugins.spring).apply(false)
    alias(libs.plugins.spring.dependency).apply(false)
    alias(libs.plugins.kotlin.plugin.spring).apply(false)
}

allprojects {
    apply<KotlinPlatformJvmPlugin>()
    apply<SonarQubePlugin>()

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
    apply<IdeaPlugin>()
    apply<JacocoPlugin>()
    apply<DokkaPlugin>()
    apply<SpringBootPlugin>()
    apply<DependencyManagementPlugin>()
    apply<TestSetsPlugin>()
    apply<SpotlessPlugin>()

    dependencies {
        testImplementation("io.kotest:kotest-runner-junit5:5.5.4")
        testImplementation("io.kotest:kotest-assertions-core:5.5.4")
        testImplementation("io.kotest:kotest-property:5.5.4")
        testImplementation("io.github.serpro69:kotlin-faker:1.13.0")
        testImplementation("com.tngtech.archunit:archunit:1.0.1")
        testImplementation("com.tngtech.archunit:archunit-junit5:1.0.1")
    }

    testSets {
        val archTest by creating
        val itest by creating
    }

    tasks {
        withType<Test> {
            useJUnitPlatform()
            finalizedBy("jacocoTestReport")
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

    configure<SpotlessExtension> {
        kotlin {
            ktfmt()
            ktlint()
            diktat()
            licenseHeader("/* (C)$YEAR */")
        }
        kotlinGradle {
            target("*.gradle.kts")
            ktlint()
        }
    }

    val bootJar: BootJar by tasks
    bootJar.enabled = false
}

tasks {
    check {
        dependsOn(
            "spotlessCheck",
            allprojects.map { it.tasks.withType<Test>() },
        )
    }

    register<JacocoReport>("coverage") {
        group = "coverage"
        description = "Test Coverage"
        dependsOn(check)
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

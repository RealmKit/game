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
import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val sourceCompatibility: String by project

// Plugins
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    // Kotlin
    idea
    alias(libs.plugins.kotlin.jvm)
    // Code Quality
    jacoco
    alias(libs.plugins.quality.versions)
    alias(libs.plugins.quality.catalog)
    alias(libs.plugins.quality.spotless)
    alias(libs.plugins.quality.dokka)
    alias(libs.plugins.quality.detekt)
    alias(libs.plugins.quality.sonarqube)
    // Spring
    alias(libs.plugins.spring.dependency)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.kotlin.spring)
}

// Repositories
repositories {
    mavenCentral()
    gradlePluginPortal()
}

// Dependencies
dependencies {
    // Annotations Processors
    annotationProcessor(libs.spring.boot.processor)

    // Runtime Dependencies
    runtimeOnly(libs.kotlin.reflect)

    // Code Dependencies
    implementation(libs.bundles.spring.boot)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlin.logging)

    // Test Dependencies
    testImplementation(libs.bundles.test.spring.boot)
    testImplementation(libs.bundles.test.kotest)
    testImplementation(libs.bundles.test.archunit)
    testImplementation(libs.bundles.test.testcontainers)
    testImplementation(libs.test.faker)

    // Code Quality
    detektPlugins(libs.bundles.quality.deteket)
}

// Configuration
configure<SpotlessExtension> {
    kotlin {
        target("**/*.kt")
        ktfmt()
        ktlint()
        diktat().configFile("diktat-analysis.yml")
        trimTrailingWhitespace()
        endWithNewline()
    }
    kotlinGradle {
        target("*.gradle.kts")
        ktlint()
    }
    format("misc") {
        target("*.md", "*.yml", "*.properties", ".gitignore")
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
    }
}

detekt {
    config = files("$rootDir/detekt-config.yml")
}

// Tasks
tasks {
    // Compile
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = sourceCompatibility
        }
    }

    // Testing
    withType<Test> {
        useJUnitPlatform()
    }

    withType<Detekt> {
        reports {
            sarif.required.set(true)
        }
    }

    // Default Tasks
    check {
        dependsOn(
            detekt,
            spotlessCheck,
            test,
        )
        finalizedBy(
            dependencyUpdates,
        )
    }

    test {
        finalizedBy(
            jacocoTestReport,
        )
    }

    jacocoTestReport {
        reports {
            xml.required.set(true)
        }
    }
}

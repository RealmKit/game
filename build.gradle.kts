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
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val sourceCompatibility: String by project

/**
 * Plugins
 */
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    // Kotlin
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    // Code Quality
    alias(libs.plugins.quality.versions)
    alias(libs.plugins.quality.catalog)
    alias(libs.plugins.quality.spotless)
    // Spring
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency)
}

/**
 * Repositories
 */
repositories {
    mavenCentral()
}

/**
 * Dependencies
 */
dependencies {
    // Annotations Processors
    annotationProcessor(libs.spring.boot.processor)

    // Runtime Dependencies
    runtimeOnly(libs.kotlin.reflect)

    // Code Dependencies
    implementation(libs.spring.boot.starter)
}

/**
 * Configuration
 */
configure<SpotlessExtension> {
    kotlin {
        target("**/*.kt")
        ktfmt()
        ktlint()
        diktat()
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

/**
 * Tasks
 */
tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = sourceCompatibility
        }
    }

    withType<Test> {
        useJUnitPlatform()
    }

    check {
        finalizedBy(":versionCatalogUpdate")
    }
}

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

// Plugins
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    // Kotlin
    alias(libs.plugins.kotlin.jvm)
    // Code Quality
    alias(libs.plugins.quality.versions)
    alias(libs.plugins.quality.catalog)
    alias(libs.plugins.quality.spotless)
    alias(libs.plugins.quality.dokka)
    alias(libs.plugins.quality.detekt)
    // Spring
    alias(libs.plugins.spring.dependency)
    alias(libs.plugins.spring.boot).apply(false)
    alias(libs.plugins.kotlin.spring).apply(false)
}

// Sub Projects
allprojects {
    // Plugins
    apply(plugin = rootProject.libs.plugins.kotlin.jvm.get().pluginId)
    apply(plugin = rootProject.libs.plugins.quality.versions.get().pluginId)
    apply(plugin = rootProject.libs.plugins.quality.spotless.get().pluginId)
    apply(plugin = rootProject.libs.plugins.quality.dokka.get().pluginId)
    apply(plugin = rootProject.libs.plugins.quality.detekt.get().pluginId)
    apply(plugin = rootProject.libs.plugins.spring.dependency.get().pluginId)

    // Repositories
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    // Dependencies
    dependencies {
        // Runtime Dependencies
        runtimeOnly(rootProject.libs.kotlin.reflect)
    }

    // Configuration
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

    // Tasks
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
            dependsOn(
                allprojects.map { it.tasks.named("detekt") },
                allprojects.map { it.tasks.named("spotlessCheck") },
                allprojects.map { it.tasks.withType<Test>() },
            )
            finalizedBy(":versionCatalogUpdate")
        }
    }
}

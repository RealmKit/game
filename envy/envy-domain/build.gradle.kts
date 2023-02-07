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

dependencies {
    /**
     * MAIN: ANNOTATIONS
     */
    annotationProcessor(rootProject.libs.spring.processor)

    /**
     * MAIN: IMPLEMENTATION
     */
    api(project(":sloth:sloth-core"))
    api(rootProject.libs.test.spring.boot)
    api(rootProject.libs.spring.data.mongodb)

    /**
     * TEST: IMPLEMENTATION
     */
    testImplementation(project(":sloth:sloth-test-utils"))
    testImplementation(project(":envy:envy-test-utils"))
    testImplementation(rootProject.libs.test.spring.boot)
    testImplementation(rootProject.libs.spring.data.mongodb)
    testImplementation(rootProject.libs.test.kotest.spring)
    testImplementation(rootProject.libs.test.testcontainers.mongodb)
}

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

package dev.realmkit.test.sloth.testutils.specs

import dev.realmkit.test.sloth.testutils.infra.Mongo
import io.kotest.core.spec.Spec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Testcontainers

/**
 * [IntegrationTestSpec]
 * This class wraps a few extra things on top of [Kotest ExpectSpec][TestSpec]
 * Use it for Integration Tests purpose, as it will start the database and
 * everything else needed by the main application to start
 */
@Testcontainers
abstract class IntegrationTestSpec(body: IntegrationTestSpec.() -> Unit = {}) : TestSpec() {
    init {
        @Suppress("UNUSED_EXPRESSION")
        body()
    }

    /**
     * Before starting the tests spec
     *
     * @param spec The test spec itself
     * @see TestSpec.beforeSpec
     */
    override suspend fun beforeSpec(spec: Spec) {
        Mongo.start()
    }

    /**
     * After finishing the tests spec
     *
     * @param spec The test spec itself
     * @see TestSpec.afterSpec
     */
    override suspend fun afterSpec(spec: Spec) {
        Mongo.stop()
    }

    /**
     * After each test on the spec
     *
     * @param testCase The test itself
     * @param result The test result
     * @see TestSpec.afterEach
     */
    override suspend fun afterEach(testCase: TestCase, result: TestResult) {
        Mongo.clear()
    }

    companion object {
        /**
         * @param registry
         * @return
         */
        @JvmStatic
        @DynamicPropertySource
        fun dynamicPropertySource(registry: DynamicPropertyRegistry) =
            registry.add("spring.data.mongodb.uri") { Mongo.container.replicaSetUrl }
    }
}

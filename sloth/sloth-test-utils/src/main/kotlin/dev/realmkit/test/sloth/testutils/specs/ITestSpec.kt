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

import dev.realmkit.test.sloth.testutils.infra.MongoDB
import io.kotest.core.spec.Spec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.property.Arb
import io.kotest.property.checkAll
import kotlinx.coroutines.coroutineScope

/**
 * [ITestSpec]
 *
 * This class wraps a few extra things on top of [Kotest ExpectSpec][TestSpec]
 * Use it for Integration Tests purpose, as it will start the database and
 * everything else needed by the main application to start
 */
abstract class ITestSpec(body: ITestSpec.() -> Unit = {}) : TestSpec() {
    init {
        body()
    }

    /**
     * Before starting the tests spec
     *
     * @param
     */
    override suspend fun beforeSpec(spec: Spec) {
        MongoDB.start
    }

    /**
     * After finishing the tests spec
     */
    override fun afterSpec(f: suspend (Spec) -> Unit) {
        MongoDB.stop
    }

    /**
     * After each test on the spec
     */
    override suspend fun afterEach(testCase: TestCase, result: TestResult) {
        MongoDB.clear
    }

    /**
     *
     */
    suspend fun context(function: () -> Unit) =
        coroutineScope { function() }

    suspend fun <T> check(arbitrary: Arb<T>, block: (T) -> Unit) {
        checkAll(arbitrary) { arb ->
            context {
                block(arb)
            }
        }
    }
}

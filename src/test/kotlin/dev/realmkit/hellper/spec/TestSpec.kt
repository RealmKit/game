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

package dev.realmkit.hellper.spec

import io.kotest.core.spec.style.ExpectSpec
import io.kotest.property.Arb
import io.kotest.property.PropertyContext
import io.kotest.property.checkAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

/**
 * [TestSpec]
 * This class wraps a few extra things on top of [Kotest ExpectSpec][ExpectSpec]
 *
 * @see ExpectSpec
 */
abstract class TestSpec(body: TestSpec.() -> Unit = {}) : ExpectSpec() {
    init {
        this.body()
    }

    /**
     * Wraps a [coroutine scope][CoroutineScope]
     *
     * @param block the block of code
     * @return the function result
     * @see CoroutineScope
     */
    suspend fun context(block: () -> Unit): Unit =
        coroutineScope { block() }

    /**
     * Execute a checkAll arbitrary from Kotest
     * This will iterate hundreds of times over the same test
     *
     * @param arbitrary the arbitrary class
     * @param block the block of tests
     * @return the [PropertyContext]
     * @see Arb
     */
    suspend fun <T> check(arbitrary: Arb<T>, block: PropertyContext.(T) -> Unit): PropertyContext =
        checkAll(arbitrary) { arb ->
            context {
                block(arb)
            }
        }
}

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

package dev.realmkit.game.core.extension

import dev.realmkit.game.core.extension.ValidationExtensions.notBlank
import dev.realmkit.game.core.extension.ValidationExtensions.positive
import dev.realmkit.hellper.extension.AssertionExtensions.shouldHaveAllErrors
import dev.realmkit.hellper.spec.TestSpec
import io.konform.validation.Validation
import io.kotest.assertions.konform.shouldBeInvalid
import io.kotest.assertions.konform.shouldBeValid

class ValidationExtensionsTest : TestSpec({
    data class Dummy(val string: String, val number: Long)

    expect("to be valid") {
        val dummy = Dummy("string", 1)
        Validation {
            Dummy::string { notBlank() }
            Dummy::number { positive() }
        }.shouldBeValid(dummy)
    }

    expect("to be invalid") {
        val dummy = Dummy("", -1)
        Validation {
            Dummy::string { notBlank() }
            Dummy::number { positive() }
        }.shouldBeInvalid(dummy) {
            it shouldHaveAllErrors listOf(
                ".string" to "must not be blank",
                ".number" to "must be positive",
            )
        }
    }
})

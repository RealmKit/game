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

package dev.realmkit.hellper.extension

import dev.realmkit.hellper.extension.AssertionExtensions.shouldHaveErrors
import io.konform.validation.Invalid
import io.konform.validation.ValidationError
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

/**
 * # [Violation]
 * violation pair for [ValidationError.dataPath] to [ValidationError.message]
 */
typealias Violation = Pair<String, String>

/**
 * # [AssertionExtensions]
 */
object AssertionExtensions {
    /**
     * ## [shouldHaveAllErrors]
     *
     * @param violations the list of violations pair for [ValidationError.dataPath] to [ValidationError.message]
     * @return the validation
     */
    infix fun Invalid<*>.shouldHaveAllErrors(violations: List<Violation>) {
        violations.forEachIndexed { index, violation ->
            this shouldContainFieldError violation
        }
        this shouldHaveErrors violations.size
    }

    /**
     * ## [shouldContainFieldError]
     *
     * @param violation the error pair for [ValidationError.dataPath] to [ValidationError.message]
     * @return the validation
     */
    private infix fun Invalid<*>.shouldContainFieldError(violation: Violation) =
        errors.first { it.dataPath == violation.first }
            .shouldNotBeNull()
            .message shouldBe violation.second

    /**
     * ## [shouldHaveErrors]
     *
     * @param size the number of validation errors
     * @return the validation
     */
    private infix fun Invalid<*>.shouldHaveErrors(size: Int) =
        errors shouldHaveSize size
}

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

package dev.realmkit.game.core.extension.validator

import dev.realmkit.game.domain.aliases.CurrentMaxDouble
import dev.realmkit.hellper.extension.AssertionExtensions.shouldHaveAllErrors
import dev.realmkit.hellper.fixture.core.fixture
import dev.realmkit.hellper.fixture.core.invalid
import dev.realmkit.hellper.spec.TestSpec
import io.kotest.assertions.konform.shouldBeInvalid
import io.kotest.assertions.konform.shouldBeValid

class CurrentMaxValidatorTest : TestSpec({
    context("unit testing CurrentMaxValidator") {
        context("CurrentMaxDouble") {
            context("isValid") {
                expect("currentMax to be valid") {
                    check(CurrentMaxDouble.fixture) { currentMax ->
                        CurrentMaxValidator.validation shouldBeValid currentMax
                    }
                }
            }

            context("isInvalid") {
                expect("currentMax to be invalid") {
                    check(CurrentMaxDouble.invalid) { currentMax ->
                        CurrentMaxValidator.validation.shouldBeInvalid(currentMax) { invalid ->
                            invalid shouldHaveAllErrors listOf(
                                "" to ".current must be lower than .max",
                                ".max" to "must be positive",
                            )
                        }
                    }
                }
            }
        }
    }
})

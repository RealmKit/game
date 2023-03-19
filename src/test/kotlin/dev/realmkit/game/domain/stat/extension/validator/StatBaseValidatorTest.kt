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

package dev.realmkit.game.domain.stat.extension.validator

import dev.realmkit.game.domain.stat.document.StatBase
import dev.realmkit.hellper.extension.AssertionExtensions.shouldHaveAllErrors
import dev.realmkit.hellper.fixture.stat.fixture
import dev.realmkit.hellper.fixture.stat.invalid
import dev.realmkit.hellper.spec.TestSpec
import io.kotest.assertions.konform.shouldBeInvalid
import io.kotest.assertions.konform.shouldBeValid
import io.kotest.property.checkAll

class StatBaseValidatorTest : TestSpec({
    expect("base to be valid") {
        checkAll(StatBase.fixture) { base ->
            StatBaseValidator.validation shouldBeValid base
        }
    }

    expect("base to be invalid") {
        checkAll(StatBase.invalid) { base ->
            StatBaseValidator.validation.shouldBeInvalid(base) { invalid ->
                invalid shouldHaveAllErrors listOf(
                    ".hull" to ".current must be lower than .max",
                    ".hull.max" to "must be positive",
                    ".shield" to ".current must be lower than .max",
                    ".shield.max" to "must be positive",
                    ".energy" to ".current must be lower than .max",
                    ".energy.max" to "must be positive",
                    ".attack" to "must be positive",
                    ".defense" to "must be positive",
                    ".speed" to "must be positive",
                    ".aggro" to "must be positive",
                )
            }
        }
    }
})

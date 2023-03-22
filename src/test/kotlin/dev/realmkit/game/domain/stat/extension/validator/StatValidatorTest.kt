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

import dev.realmkit.game.domain.stat.document.Stat
import dev.realmkit.hellper.extension.AssertionExtensions.shouldHaveAllErrors
import dev.realmkit.hellper.fixture.stat.StatFixture.fixture
import dev.realmkit.hellper.fixture.stat.StatFixture.invalid
import dev.realmkit.hellper.spec.TestSpec
import io.kotest.assertions.konform.shouldBeInvalid
import io.kotest.assertions.konform.shouldBeValid
import io.kotest.property.checkAll

class StatValidatorTest : TestSpec({
    expect("stat to be valid") {
        checkAll(Stat.fixture) { stat ->
            StatValidator.validation shouldBeValid stat
        }
    }

    expect("stat to be invalid") {
        checkAll(Stat.invalid) { stat ->
            StatValidator.validation.shouldBeInvalid(stat) { invalid ->
                invalid shouldHaveAllErrors listOf(
                    ".base.hull" to ".current must be lower than .max",
                    ".base.hull.max" to "must be positive",
                    ".base.shield" to ".current must be lower than .max",
                    ".base.shield.max" to "must be positive",
                    ".base.energy" to ".current must be lower than .max",
                    ".base.energy.max" to "must be positive",
                    ".base.attack" to "must be positive",
                    ".base.defense" to "must be positive",
                    ".base.speed" to "must be positive",
                    ".base.aggro" to "must be positive",
                    ".rate.shieldRegeneration" to "must be positive",
                    ".rate.critical" to "must be positive",
                    ".multiplier.critical" to "must be positive",
                    ".progression.level" to "must be positive",
                    ".progression.experience" to "must be positive",
                    ".progression.points" to "must be positive",
                )
            }
        }
    }
})

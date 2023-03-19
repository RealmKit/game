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

package dev.realmkit.game.domain.player.extension

import dev.realmkit.game.core.exception.ValidationException
import dev.realmkit.game.domain.player.document.Player
import dev.realmkit.game.domain.player.extension.PlayerValidator.validated
import dev.realmkit.hellper.extension.AssertionExtensions.shouldHaveAllErrors
import dev.realmkit.hellper.fixture.player.fixture
import dev.realmkit.hellper.fixture.player.invalid
import dev.realmkit.hellper.spec.TestSpec
import io.kotest.assertions.konform.shouldBeInvalid
import io.kotest.assertions.konform.shouldBeValid
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.property.checkAll

class PlayerValidatorTest : TestSpec({
    expect("to validate a Player") {
        checkAll(Player.fixture) { player ->
            player validated { validated -> validated.shouldNotBeNull() }
        }
    }

    expect("player to be valid") {
        checkAll(Player.fixture) { player ->
            PlayerValidator.validation shouldBeValid player
        }
    }

    expect("to throw a ValidationException when validating a Player") {
        checkAll(Player.fixture) { player ->
            shouldThrow<ValidationException> {
                player.copy(name = "") validated { null }
            }.shouldNotBeNull()
                .invalid.shouldNotBeNull()
                .errors.shouldNotBeNull().shouldNotBeEmpty()
        }
    }

    expect("player to be invalid") {
        checkAll(Player.invalid) { player ->
            PlayerValidator.validation.shouldBeInvalid(player) { invalid ->
                invalid shouldHaveAllErrors listOf(
                    ".name" to "must not be blank",
                    ".stat.base.hull" to ".current must be lower than .max",
                    ".stat.base.hull.max" to "must be positive",
                    ".stat.base.shield" to ".current must be lower than .max",
                    ".stat.base.shield.max" to "must be positive",
                    ".stat.base.energy" to ".current must be lower than .max",
                    ".stat.base.energy.max" to "must be positive",
                    ".stat.base.attack" to "must be positive",
                    ".stat.base.defense" to "must be positive",
                    ".stat.base.speed" to "must be positive",
                    ".stat.base.aggro" to "must be positive",
                    ".stat.rate.shieldRegeneration" to "must be positive",
                    ".stat.rate.critical" to "must be positive",
                    ".stat.multiplier.critical" to "must be positive",
                    ".stat.progression.level" to "must be positive",
                    ".stat.progression.experience" to "must be positive",
                    ".resource.titanium" to "must be positive",
                    ".resource.crystal" to "must be positive",
                    ".resource.darkMatter" to "must be positive",
                    ".resource.antiMatter" to "must be positive",
                    ".resource.purunhalium" to "must be positive",
                )
            }
        }
    }
})

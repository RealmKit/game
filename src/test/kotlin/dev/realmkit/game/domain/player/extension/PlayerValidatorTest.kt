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
import dev.realmkit.game.domain.stat.document.Stat
import dev.realmkit.game.domain.stat.document.StatProgression
import dev.realmkit.hellper.extension.AssertionExtensions.shouldContainFieldError
import dev.realmkit.hellper.fixture.player.arbitrary
import dev.realmkit.hellper.fixture.player.fixture
import dev.realmkit.hellper.fixture.stat.fixture
import dev.realmkit.hellper.spec.TestSpec
import io.kotest.assertions.konform.shouldBeInvalid
import io.kotest.assertions.konform.shouldBeValid
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.nulls.shouldNotBeNull

class PlayerValidatorTest : TestSpec({
    context("unit testing PlayerValidator") {
        expect("to validate a Player") {
            check(Player.arbitrary) { player ->
                player validated { validated -> validated.shouldNotBeNull() }
            }
        }

        expect("to throw a ValidationException when validating a Player") {
            check(Player.arbitrary) { player ->
                shouldThrow<ValidationException> {
                    player.copy(name = "") validated { null }
                }.shouldNotBeNull()
                    .invalid.shouldNotBeNull()
                    .errors.shouldNotBeNull().shouldNotBeEmpty()
            }
        }

        expect("player to be valid") {
            check(Player.arbitrary) { player ->
                PlayerValidator.validation shouldBeValid player
            }
        }

        expect("player to be invalid") {
            val player = Player.fixture(
                name = "",
                stat = Stat.fixture(
                    progression = StatProgression.fixture(
                        level = -1,
                        experience = -1,
                    ),
                ),
            )

            PlayerValidator.validation.shouldBeInvalid(player) {
                it shouldContainFieldError (".name" to "must not be blank")
                it shouldContainFieldError (".stat.progression.level" to "must be positive")
                it shouldContainFieldError (".stat.progression.experience" to "must be positive")
            }
        }
    }
})

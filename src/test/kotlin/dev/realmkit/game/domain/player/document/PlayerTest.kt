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

package dev.realmkit.game.domain.player.document

import dev.realmkit.game.domain.player.extension.PlayerValidator.validation
import dev.realmkit.game.domain.stat.document.Stat
import dev.realmkit.game.domain.stat.document.StatProgression
import dev.realmkit.hellper.extension.AssertionExtensions.shouldContainFieldError
import dev.realmkit.hellper.fixture.player.arbitrary
import dev.realmkit.hellper.fixture.player.fixture
import dev.realmkit.hellper.fixture.stat.fixture
import dev.realmkit.hellper.spec.TestSpec
import io.kotest.assertions.konform.shouldBeInvalid
import io.kotest.assertions.konform.shouldBeValid
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotBeEmpty

class PlayerTest : TestSpec({
    context("unit testing Player") {
        expect("to create a new plain Player") {
            check(Player.arbitrary) { player ->
                player.shouldNotBeNull()
                player.name.shouldNotBeNull().shouldNotBeEmpty()
                player.stat.shouldNotBeNull()
                player.stat.progression.shouldNotBeNull()
                player.stat.progression.level.shouldBeGreaterThan(0)
                player.stat.progression.experience.shouldBeGreaterThan(0)
            }
        }

        expect("to validate a Player") {
            check(Player.arbitrary) { player ->
                validation shouldBeValid player
            }
        }

        expect("to validate an invalid Player") {
            val player = Player.fixture(
                name = "",
                stat = Stat.fixture(
                    progression = StatProgression.fixture(
                        level = -1,
                        experience = -1,
                    ),
                ),
            )

            validation.shouldBeInvalid(player) {
                it shouldContainFieldError (".name" to "must not be blank")
                it shouldContainFieldError (".stat.progression.level" to "must be positive")
                it shouldContainFieldError (".stat.progression.experience" to "must be positive")
            }
        }
    }
})

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

package dev.realmkit.game.domain.target.extension

import dev.realmkit.game.domain.aliases.AttackerTargets
import dev.realmkit.game.domain.player.document.Player
import dev.realmkit.game.domain.target.extension.TargetExtensions.bySpeed
import dev.realmkit.game.domain.target.extension.TargetExtensions.firstByAggro
import dev.realmkit.game.domain.target.extension.TargetExtensions.hasAlive
import dev.realmkit.hellper.fixture.player.fixture
import dev.realmkit.hellper.fixture.player.invalid
import dev.realmkit.hellper.spec.TestSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.property.checkAll

class TargetExtensionsTest : TestSpec({
    context("unit testing TargetExtensions") {
        context(".hasAlive") {
            expect("to have an alive Target") {
                check(Player.fixture) { player ->
                    listOf(player).hasAlive.shouldBeTrue()
                }
            }

            expect("to have several alive Target") {
                checkAll(Player.fixture, Player.fixture) { player1, player2 ->
                    listOf(player1, player2).hasAlive.shouldBeTrue()
                }
            }

            expect("to have just one alive Target") {
                checkAll(Player.fixture, Player.invalid) { player1, player2 ->
                    listOf(player1, player2).hasAlive.shouldBeTrue()
                }
            }

            expect("to have no alive Target") {
                checkAll(Player.invalid, Player.invalid) { player1, player2 ->
                    listOf(player1, player2).hasAlive.shouldBeFalse()
                }
            }
        }

        context(".bySpeed") {
            checkAll(Player.fixture, Player.invalid, Player.invalid) { player1, player2, player3 ->
                listOf<AttackerTargets>(
                    player2 to emptySet(),
                    player3 to emptySet(),
                    player1 to emptySet(),
                ).bySpeed
                    .first()
                    .shouldNotBeNull()
                    .first
                    .shouldNotBeNull()
                    .shouldBe(player1)
            }
        }

        context(".firstByAggro") {
            checkAll(Player.fixture, Player.invalid, Player.invalid) { player1, player2, player3 ->
                listOf(
                    player2,
                    player3,
                    player1,
                ).firstByAggro { player ->
                    player shouldBe player1
                }.shouldNotBeNull()
                    .shouldBe(player1)
            }
        }

        context(".firstByAggro") {
            emptyList<Player>().firstByAggro {
                throw AssertionError("Should not be called")
            }.shouldBeNull()
        }
    }
})

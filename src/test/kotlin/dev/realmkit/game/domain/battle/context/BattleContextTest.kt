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

package dev.realmkit.game.domain.battle.context

import dev.realmkit.game.domain.battle.action.BattleActionAttack
import dev.realmkit.game.domain.battle.action.BattleActionAttackerAttempt
import dev.realmkit.game.domain.battle.action.BattleActionAttackerRepeatAttempt
import dev.realmkit.game.domain.player.document.Player
import dev.realmkit.game.domain.staticdata.document.StaticDataBattle
import dev.realmkit.hellper.extension.AssertionExtensions.onAction
import dev.realmkit.hellper.extension.AssertionExtensions.onTurn
import dev.realmkit.hellper.extension.AssertionExtensions.shouldBeAlive
import dev.realmkit.hellper.extension.AssertionExtensions.shouldHaveTurns
import dev.realmkit.hellper.extension.AssertionExtensions.shouldNotBeAlive
import dev.realmkit.hellper.fixture.player.fixture
import dev.realmkit.hellper.spec.TestSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.property.checkAll

class BattleContextTest : TestSpec({
    context("unit testing BattleContext") {
        context(".start()") {
            expect("One vs One battle, all Attackers win") {
                checkAll(
                    Player.fixture,
                    Player.fixture,
                ) { player, enemy ->
                    player.stat.base.power = 100.0
                    player.stat.base.speed = 1.0

                    val context = BattleContext(
                        properties = StaticDataBattle(
                            battleDuration = 10,
                            turnDuration = 10,
                        ),
                        onAttack = { attacker, defender ->
                            BattleActionAttack(
                                attacker = attacker,
                                defender = defender,
                            ).apply {
                                finalDamage = attacker.stat.base.power
                                toTheShield = false
                                isCritical = true
                                defender.stat.base.hull.current -= finalDamage
                            }
                        },
                    )
                    context.apply { player against enemy }
                    context.start()
                        .shouldHaveTurns(1)
                        .onTurn(turn = 1, actions = 4) {
                            onAction<BattleActionAttackerAttempt> {
                                attacker shouldBe player.id
                                speed shouldBe player.stat.base.speed
                                hit.shouldBeTrue()
                            }
                            onAction<BattleActionAttackerRepeatAttempt> {
                                attacker shouldBe player.id
                                hull shouldBe player.stat.base.hull.current
                                shield shouldBe player.stat.base.shield.current
                                alive.shouldBeTrue()
                            }
                            onAction<BattleActionAttack> {
                                attacker shouldBe player
                                defender shouldBe enemy
                                finalDamage shouldBe player.stat.base.power
                                toTheShield.shouldBeFalse()
                                isCritical.shouldBeTrue()
                            }
                            onAction<BattleActionAttackerAttempt> {
                                attacker shouldBe enemy.id
                                speed shouldBe enemy.stat.base.speed
                                hit.shouldBeFalse()
                            }
                        }

                    player.shouldBeAlive()
                    enemy.shouldNotBeAlive()
                }
            }
        }
    }
})

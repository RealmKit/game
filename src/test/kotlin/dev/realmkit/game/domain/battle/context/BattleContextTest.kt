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

import dev.realmkit.game.domain.player.document.Player
import dev.realmkit.game.domain.staticdata.document.StaticDataBattle
import dev.realmkit.hellper.extension.AssertionExtensions.shouldBeAlive
import dev.realmkit.hellper.extension.AssertionExtensions.shouldNotBeAlive
import dev.realmkit.hellper.fixture.player.fixture
import dev.realmkit.hellper.spec.TestSpec
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
                            defender.stat.base.hull.current -= attacker.stat.base.power
                            attacker.stat.base.power
                        },
                    )
                    context.apply { player against enemy }
                    context.start()

                    player.shouldBeAlive()
                    enemy.shouldNotBeAlive()
                }
            }
        }
    }
})

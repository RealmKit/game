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

package dev.realmkit.game.domain.battle.service

import dev.realmkit.game.domain.player.document.Player
import dev.realmkit.hellper.extension.AssertionExtensions.shouldBeAlive
import dev.realmkit.hellper.extension.AssertionExtensions.shouldBeDead
import dev.realmkit.hellper.fixture.player.fixture
import dev.realmkit.hellper.infra.IntegrationTestContext
import dev.realmkit.hellper.spec.IntegrationTestSpec
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.checkAll

@IntegrationTestContext
class BattleServiceTest(
    private val battleService: BattleService,
) : IntegrationTestSpec({
    context("integration testing BattleService") {
        expect("battle: One versus One") {
            checkAll(
                arbitrary { Player.fixture },
                arbitrary { Player.fixture },
            ) { player, enemy ->
                player.stat.base.shield.current = 100.0
                player.stat.base.power = 100.0
                player.stat.base.defense = 100.0
                player.stat.base.speed = 1.0

                battleService.battle { player against enemy }

                player.shouldBeAlive()
                enemy.shouldBeDead()
            }
        }

        expect("battle: One versus Many") {
            checkAll(
                arbitrary { Player.fixture },
                arbitrary { Player.fixture },
                arbitrary { Player.fixture },
            ) { player, enemy1, enemy2 ->
                player.stat.base.shield.current = 100.0
                player.stat.base.power = 100.0
                player.stat.base.defense = 100.0
                player.stat.base.speed = 1.0

                battleService.battle { player against listOf(enemy1, enemy2) }

                player.shouldBeAlive()
                enemy1.shouldBeDead()
                enemy2.shouldBeDead()
            }
        }

        expect("battle: Many versus One") {
            checkAll(
                arbitrary { Player.fixture },
                arbitrary { Player.fixture },
                arbitrary { Player.fixture },
            ) { player1, player2, enemy ->
                player1.stat.base.shield.current = 100.0
                player1.stat.base.power = 100.0
                player1.stat.base.defense = 100.0
                player1.stat.base.speed = 1.0
                player2.stat.base.defense = 100.0
                player2.stat.base.speed = 1.0

                battleService.battle { listOf(player1, player2) against enemy }

                player1.shouldBeAlive()
                player2.shouldBeAlive()
                enemy.shouldBeDead()
            }
        }

        expect("battle: Many versus Many") {
            checkAll(
                arbitrary { Player.fixture },
                arbitrary { Player.fixture },
                arbitrary { Player.fixture },
                arbitrary { Player.fixture },
            ) { player1, player2, enemy1, enemy2 ->
                player1.stat.base.shield.current = 100.0
                player1.stat.base.power = 100.0
                player1.stat.base.defense = 100.0
                player1.stat.base.speed = 2.0
                player2.stat.base.defense = 100.0
                player2.stat.base.speed = 1.0

                enemy1.stat.base.speed = 1.0
                enemy2.stat.base.speed = 0.5

                battleService.battle { listOf(player1, player2) against listOf(enemy1, enemy2) }

                player1.shouldBeAlive()
                player2.shouldBeAlive()
                enemy1.shouldBeDead()
                enemy2.shouldBeDead()
            }
        }
    }
})

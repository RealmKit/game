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
import dev.realmkit.game.domain.enemy.document.Enemy
import dev.realmkit.game.domain.player.document.Player
import dev.realmkit.hellper.extension.AssertionExtensions.onAction
import dev.realmkit.hellper.extension.AssertionExtensions.onTurn
import dev.realmkit.hellper.extension.AssertionExtensions.shouldBeAlive
import dev.realmkit.hellper.extension.AssertionExtensions.shouldHaveTurns
import dev.realmkit.hellper.extension.AssertionExtensions.shouldNotBeAlive
import dev.realmkit.hellper.fixture.battle.DEFAULT_BATTLE_DURATION
import dev.realmkit.hellper.fixture.battle.fixture
import dev.realmkit.hellper.fixture.enemy.fixture
import dev.realmkit.hellper.fixture.enemy.many
import dev.realmkit.hellper.fixture.player.fixture
import dev.realmkit.hellper.fixture.stat.prepareToWinBattle
import dev.realmkit.hellper.spec.TestSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.shouldBe
import io.kotest.property.checkAll

class BattleContextTest : TestSpec({
    expect("One vs One battle, all Attackers win") {
        checkAll(
            Player.fixture,
            Enemy.fixture,
            BattleContext.fixture,
        ) { player, enemy, context ->
            player.stat.prepareToWinBattle()

            context.apply { player against enemy }
                .start()
                .shouldHaveTurns(1)
                .onTurn(turn = 1, actions = 1) {
                    onAction<BattleActionAttack> {
                        attacker shouldBe player
                        defender shouldBe enemy
                        finalDamage shouldBe player.stat.base.attack
                        toTheShield.shouldBeFalse()
                        isCritical.shouldBeFalse()
                    }
                }

            player.shouldBeAlive()
            enemy.shouldNotBeAlive()
        }
    }

    expect("One vs One battle, all Defenders win") {
        checkAll(
            Player.fixture,
            Enemy.fixture,
            BattleContext.fixture,
        ) { player, enemy, context ->
            enemy.stat.base.attack = 100.0
            enemy.stat.base.speed = 1.0

            context.apply { player against enemy }
                .start()
                .shouldHaveTurns(1)
                .onTurn(turn = 1, actions = 1) {
                    onAction<BattleActionAttack> {
                        attacker shouldBe enemy
                        defender shouldBe player
                        finalDamage shouldBe enemy.stat.base.attack
                        toTheShield.shouldBeFalse()
                        isCritical.shouldBeFalse()
                    }
                }

            player.shouldNotBeAlive()
            enemy.shouldBeAlive()
        }
    }

    expect("One vs One battle, draw, all alive") {
        checkAll(
            Player.fixture,
            Enemy.fixture,
            BattleContext.fixture,
        ) { player, enemy, context ->
            player.stat.base.speed = 0.0
            enemy.stat.base.speed = 0.0

            context.apply { player against enemy }
                .start()
                .shouldHaveTurns(DEFAULT_BATTLE_DURATION)
                .onTurn(turn = 1, actions = 0)
                .onTurn(turn = 2, actions = 0)
                .onTurn(turn = 3, actions = 0)
                .onTurn(turn = 4, actions = 0)
                .onTurn(turn = 5, actions = 0)

            player.shouldBeAlive()
            enemy.shouldBeAlive()
        }
    }

    expect("One vs One battle, draw, some alive") {
        checkAll(
            Player.fixture,
            Enemy.many(),
            BattleContext.fixture,
        ) { player, enemies, context ->
            player.stat.prepareToWinBattle()
            enemies.onEach { enemy -> enemy.stat.base.speed = 0.0 }

            context.apply { player against enemies }
                .start()
                .shouldHaveTurns(DEFAULT_BATTLE_DURATION)
                .onTurn(turn = 1, actions = 1) { onAction<BattleActionAttack>() }
                .onTurn(turn = 2, actions = 1) { onAction<BattleActionAttack>() }
                .onTurn(turn = 3, actions = 1) { onAction<BattleActionAttack>() }
                .onTurn(turn = 4, actions = 1) { onAction<BattleActionAttack>() }
                .onTurn(turn = 5, actions = 1) { onAction<BattleActionAttack>() }

            player.shouldBeAlive()
        }
    }

    expect("One vs Many battle, all Attackers win") {
        checkAll(
            Player.fixture,
            Enemy.fixture,
            Enemy.fixture,
            Enemy.fixture,
            BattleContext.fixture,
        ) { player, enemy1, enemy2, enemy3, context ->
            player.stat.prepareToWinBattle()
            enemy1.stat.base.aggro = 1.0
            enemy2.stat.base.aggro = 2.0
            enemy3.stat.base.aggro = 3.0

            context.apply { player against listOf(enemy1, enemy2, enemy3) }
                .start()
                .shouldHaveTurns(3)
                .onTurn(turn = 1, actions = 1) {
                    onAction<BattleActionAttack> {
                        attacker shouldBe player
                        defender shouldBe enemy3
                        finalDamage shouldBe player.stat.base.attack
                        toTheShield.shouldBeFalse()
                        isCritical.shouldBeFalse()
                    }
                }.onTurn(turn = 2, actions = 1) {
                    onAction<BattleActionAttack> {
                        attacker shouldBe player
                        defender shouldBe enemy2
                        finalDamage shouldBe player.stat.base.attack
                        toTheShield.shouldBeFalse()
                        isCritical.shouldBeFalse()
                    }
                }.onTurn(turn = 3, actions = 1) {
                    onAction<BattleActionAttack> {
                        attacker shouldBe player
                        defender shouldBe enemy1
                        finalDamage shouldBe player.stat.base.attack
                        toTheShield.shouldBeFalse()
                        isCritical.shouldBeFalse()
                    }
                }

            player.shouldBeAlive()
            enemy1.shouldNotBeAlive()
            enemy2.shouldNotBeAlive()
            enemy3.shouldNotBeAlive()
        }
    }
})

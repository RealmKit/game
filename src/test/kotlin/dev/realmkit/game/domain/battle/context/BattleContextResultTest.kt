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
import dev.realmkit.hellper.fixture.enemy.fixture
import dev.realmkit.hellper.fixture.player.fixture
import dev.realmkit.hellper.spec.TestSpec
import io.kotest.assertions.asClue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.maps.shouldBeEmpty
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.property.checkAll

class BattleContextResultTest : TestSpec({
    expect("should create a new BattleContextResult") {
        BattleContextResult().asClue { context ->
            context.attackers.shouldNotBeNull().shouldBeEmpty()
            context.defenders.shouldNotBeNull().shouldBeEmpty()
            context.logsPerTurn.shouldNotBeNull().shouldBeEmpty()
            context.turns shouldBe 0L
        }
    }

    expect("should register a new turn") {
        val context = BattleContextResult()
        context.turns shouldBe 0L
        context.logsPerTurn.shouldBeEmpty()

        context.registerTurn()
        context.turns shouldBe 1L
        context.logsPerTurn.shouldHaveSize(1)
    }

    expect("should register a attack result") {
        checkAll(Player.fixture, Enemy.fixture) { player, enemy ->
            val context = BattleContextResult()
            context.registerTurn()
            context.registerAttackResults(
                BattleActionAttack(
                    attacker = player,
                    defender = enemy,
                    finalDamage = 0.0,
                    toTheShield = false,
                    isCritical = false,
                ),
            )

            context.turns shouldBe 1L
            context.logsPerTurn.shouldHaveSize(1)
            context.logsPerTurn[1]!!
                .shouldNotBeNull().shouldHaveSize(1)
                .first()
                .shouldNotBeNull().shouldBeInstanceOf<BattleActionAttack>()
        }
    }

    expect("should start") {
        checkAll(Player.fixture, Enemy.fixture) { player, enemy ->
            val context = BattleContextResult()
            context.start(setOf(player), setOf(enemy))

            context.attackers shouldBe setOf(player)
            context.defenders shouldBe setOf(enemy)
        }
    }
})

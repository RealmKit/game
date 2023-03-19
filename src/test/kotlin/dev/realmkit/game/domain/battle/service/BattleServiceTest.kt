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

import dev.realmkit.game.domain.base.extension.MongoRepositoryExtensions.persist
import dev.realmkit.game.domain.battle.enums.BattleActionFinalResultType.ATTACKERS_WIN
import dev.realmkit.game.domain.battle.enums.BattleActionFinalResultType.DEFENDERS_WIN
import dev.realmkit.game.domain.battle.enums.BattleActionFinalResultType.DRAW
import dev.realmkit.game.domain.player.document.Player
import dev.realmkit.game.domain.player.repository.PlayerRepository
import dev.realmkit.hellper.extension.AssertionExtensions.shouldBeAlive
import dev.realmkit.hellper.extension.AssertionExtensions.shouldNotBeAlive
import dev.realmkit.hellper.fixture.player.fixture
import dev.realmkit.hellper.fixture.player.prepareToWinBattle
import dev.realmkit.hellper.infra.IntegrationTestContext
import dev.realmkit.hellper.spec.IntegrationTestSpec
import io.kotest.assertions.withClue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.property.checkAll

@IntegrationTestContext
class BattleServiceTest(
    private val playerRepository: PlayerRepository,
    private val battleService: BattleService,
) : IntegrationTestSpec({
    expect("all beans to be inject") {
        battleService.shouldNotBeNull()
    }

    expect("One vs One battle, all Attackers win") {
        checkAll(
            Player.fixture,
            Player.fixture,
        ) { player, enemy ->
            player.prepareToWinBattle()
            playerRepository persist player

            battleService.battle { player against enemy }
                .finalResult.shouldNotBeNull()
                .result shouldBe ATTACKERS_WIN

            withClue("player") { player.shouldBeAlive() }
            withClue("enemy") { enemy.shouldNotBeAlive() }
        }
    }

    expect("One versus Many, all Attackers win") {
        checkAll(
            Player.fixture,
            Player.fixture,
            Player.fixture,
        ) { player, enemy1, enemy2 ->
            player.prepareToWinBattle()
            playerRepository persist player

            battleService.battle { player against listOf(enemy1, enemy2) }
                .finalResult.shouldNotBeNull()
                .result shouldBe ATTACKERS_WIN

            withClue("player") { player.shouldBeAlive() }
            withClue("enemy1") { enemy1.shouldNotBeAlive() }
            withClue("enemy2") { enemy2.shouldNotBeAlive() }
        }
    }

    expect("Many versus One, all Attackers win") {
        checkAll(
            Player.fixture,
            Player.fixture,
            Player.fixture,
        ) { player1, player2, enemy ->
            player1.prepareToWinBattle()
            playerRepository persist player1
            player2.prepareToWinBattle()
            playerRepository persist player2

            battleService.battle { listOf(player1, player2) against enemy }
                .finalResult.shouldNotBeNull()
                .result shouldBe ATTACKERS_WIN

            withClue("player1") { player1.shouldBeAlive() }
            withClue("player2") { player2.shouldBeAlive() }
            withClue("enemy") { enemy.shouldNotBeAlive() }
        }
    }

    expect("Many versus Many, all Attackers win") {
        checkAll(
            Player.fixture,
            Player.fixture,
            Player.fixture,
            Player.fixture,
        ) { player1, player2, enemy1, enemy2 ->
            player1.prepareToWinBattle()
            playerRepository persist player1
            player2.prepareToWinBattle()
            playerRepository persist player2
            enemy1.stat.base.speed = 1.0
            enemy2.stat.base.speed = 0.5

            battleService.battle { listOf(player1, player2) against listOf(enemy1, enemy2) }
                .finalResult.shouldNotBeNull()
                .result shouldBe ATTACKERS_WIN

            withClue("player1") { player1.shouldBeAlive() }
            withClue("player2") { player2.shouldBeAlive() }
            withClue("enemy1") { enemy1.shouldNotBeAlive() }
            withClue("enemy2") { enemy2.shouldNotBeAlive() }
        }
    }

    expect("Many versus Many, all Defenders win") {
        checkAll(
            Player.fixture,
            Player.fixture,
            Player.fixture,
            Player.fixture,
        ) { player1, player2, enemy1, enemy2 ->
            player1.stat.base.speed = 1.0
            player2.stat.base.speed = 0.5
            enemy1.prepareToWinBattle()
            playerRepository persist enemy1
            enemy2.prepareToWinBattle()
            playerRepository persist enemy2

            battleService.battle { listOf(player1, player2) against listOf(enemy1, enemy2) }
                .finalResult.shouldNotBeNull()
                .result shouldBe DEFENDERS_WIN

            withClue("player1") { player1.shouldNotBeAlive() }
            withClue("player2") { player2.shouldNotBeAlive() }
            withClue("enemy1") { enemy1.shouldBeAlive() }
            withClue("enemy2") { enemy2.shouldBeAlive() }
        }
    }

    expect("One versus One, battle last forever") {
        checkAll(
            Player.fixture,
            Player.fixture,
        ) { player, enemy ->
            player.stat.base.attack = 0.0
            enemy.stat.base.attack = 0.0

            battleService.battle { player against enemy }
                .finalResult.shouldNotBeNull()
                .result shouldBe DRAW

            withClue("player") { player.shouldBeAlive() }
            withClue("enemy") { enemy.shouldBeAlive() }
        }
    }
})

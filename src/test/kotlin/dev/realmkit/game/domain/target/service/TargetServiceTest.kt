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

package dev.realmkit.game.domain.target.service

import dev.realmkit.game.domain.player.document.Player
import dev.realmkit.hellper.extension.AssertionExtensions.shouldBeAlive
import dev.realmkit.hellper.extension.AssertionExtensions.shouldNotBeAlive
import dev.realmkit.hellper.fixture.player.PlayerFixture.fixture
import dev.realmkit.hellper.infra.IntegrationTestContext
import dev.realmkit.hellper.spec.IntegrationTestSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.property.checkAll

@IntegrationTestContext
class TargetServiceTest(
    private val targetService: TargetService,
) : IntegrationTestSpec({
    expect("all beans to be inject") {
        targetService.shouldNotBeNull()
    }

    expect("to not hit a when negative damage") {
        checkAll(Player.fixture, Player.fixture) { player, enemy ->
            collect(player.ship.type)

            player.ship.stat.base.attack = -1.0

            targetService.attack(player, enemy)
            enemy.shouldBeAlive()
        }
    }

    expect("to not hit a critical attack, enemy should be alive") {
        checkAll(Player.fixture, Player.fixture) { player, enemy ->
            collect(player.ship.type)

            player.ship.stat.rate.critical = 0.0
            enemy.ship.stat.base.defense = 10.0

            targetService.attack(player, enemy)
            enemy.shouldBeAlive()
        }
    }

    expect("to hit a critical attack, enemy should be alive") {
        checkAll(Player.fixture, Player.fixture) { player, enemy ->
            collect(player.ship.type)

            player.ship.stat.rate.critical = 1.0
            player.ship.stat.multiplier.critical = 2.0
            enemy.ship.stat.base.defense = 10.0

            targetService.attack(player, enemy)
            enemy.shouldBeAlive()
        }
    }

    expect("to hit a critical attack, enemy should not be alive") {
        checkAll(Player.fixture, Player.fixture) { player, enemy ->
            collect(player.ship.type)

            player.ship.stat.rate.critical = 1.0
            player.ship.stat.multiplier.critical = 2.0

            targetService.attack(player, enemy)
            enemy.shouldBeAlive()
        }
    }

    expect("to not damage a not alive Target") {
        checkAll(Player.fixture, Player.fixture) { player, enemy ->
            collect(player.ship.type)

            enemy.ship.stat.base.hull.current = 0.0
            enemy.shouldNotBeAlive()

            targetService.attack(player, enemy)
            enemy.shouldNotBeAlive()
        }
    }

    expect("Player to attack Enemy until it is not alive") {
        checkAll(Player.fixture, Player.fixture) { player, enemy ->
            collect(player.ship.type)

            player.ship.stat.base.attack = 100.0
            player.ship.stat.rate.critical = 1.0
            player.ship.stat.multiplier.critical = 1.0
            enemy.ship.stat.base.defense = 0.0

            val hull = enemy.ship.stat.base.hull.current
            targetService.attack(player, enemy)
            enemy.ship.stat.base.hull.current shouldBe hull
            enemy.shouldBeAlive()

            targetService.attack(player, enemy)
            enemy.shouldNotBeAlive()
        }
    }
})

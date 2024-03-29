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

package dev.realmkit.game.domain.player.service

import dev.realmkit.game.core.exception.NotFoundException
import dev.realmkit.game.core.exception.ValidationException
import dev.realmkit.game.domain.item.document.Item
import dev.realmkit.game.domain.item.enums.ItemTypeEnum.CHEAP_RECOVERY_DRONE
import dev.realmkit.game.domain.player.document.Player
import dev.realmkit.hellper.extension.AssertionExtensions.shouldHaveAllErrors
import dev.realmkit.hellper.fixture.player.PlayerFixture.fixture
import dev.realmkit.hellper.infra.IntegrationTestContext
import dev.realmkit.hellper.spec.IntegrationTestSpec
import io.kotest.assertions.asClue
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.doubles.shouldBeZero
import io.kotest.matchers.longs.shouldBeZero
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotBeBlank
import io.kotest.property.checkAll

@IntegrationTestContext
class PlayerServiceTest(
    private val playerService: PlayerService,
) : IntegrationTestSpec({
    expect("all beans to be inject") {
        playerService.shouldNotBeNull()
    }

    expect("it should create Players") {
        checkAll(Player.fixture) { player ->
            val saved = playerService new player.name
            saved.id.shouldNotBeNull()
            saved.createdAt.shouldNotBeNull()
            saved.updatedAt.shouldNotBeNull()
            saved.version.shouldNotBeNull()
            saved.name.shouldNotBeBlank()
            saved.ship.name.shouldNotBeBlank()
            saved.ship.stat.shouldNotBeNull()
            saved.ship.stat.base.shouldNotBeNull()
            saved.ship.stat.base.hull.max shouldBe 5.0
            saved.ship.stat.base.hull.current shouldBe 5.0
            saved.ship.stat.base.shield.max.shouldBeZero()
            saved.ship.stat.base.shield.current.shouldBeZero()
            saved.ship.stat.base.energy.max shouldBe 5.0
            saved.ship.stat.base.energy.current shouldBe 5.0
            saved.ship.stat.base.attack shouldBe 1.0
            saved.ship.stat.base.defense.shouldBeZero()
            saved.ship.stat.base.speed shouldBe 1.0
            saved.ship.stat.base.aggro shouldBe 1.0
            saved.ship.stat.rate.shouldNotBeNull()
            saved.ship.stat.rate.shieldRegeneration.shouldBeZero()
            saved.ship.stat.rate.critical.shouldBeZero()
            saved.ship.stat.multiplier.shouldNotBeNull()
            saved.ship.stat.multiplier.critical shouldBe 1.0
            saved.ship.stat.progression.level shouldBe 1
            saved.ship.stat.progression.experience.shouldBeZero()
            saved.ship.stat.progression.points.shouldBeZero()
            saved.resource.titanium shouldBe 1_000
            saved.resource.crystal.shouldBeZero()
            saved.resource.darkMatter.shouldBeZero()
            saved.resource.antiMatter.shouldBeZero()
            saved.resource.purunhalium.shouldBeZero()
        }
    }

    expect("name should not be blank") {
        shouldThrow<ValidationException> {
            playerService new ""
        }.shouldNotBeNull()
            .invalid shouldHaveAllErrors listOf(
            ".name" to "must not be blank",
        )
    }

    expect("it should level up a Player when updating it") {
        checkAll(Player.fixture) { player ->
            val saved = playerService new player.name
            saved.ship.stat.progression.level shouldBe 1
            saved.ship.stat.progression.experience.shouldBeZero()
            saved.ship.stat.progression.points.shouldBeZero()

            saved.ship.stat.progression.experience = 8
            playerService update saved
            saved.ship.stat.progression.level shouldBe 2
            saved.ship.stat.progression.experience.shouldBeZero()
            saved.ship.stat.progression.points shouldBe 5
        }
    }

    expect("to thrown a NotFoundException when updating a non existing Player") {
        checkAll(Player.fixture) { player ->
            player.id = "non-existing-id"

            shouldThrow<NotFoundException> {
                playerService update player
            }.shouldNotBeNull().asClue { exception ->
                exception.clazz shouldBe Player::class
                exception.value shouldBe player.id
            }
        }
    }

    expect("Player to receive and use an Item: CHEAP_RECOVERY_DRONE") {
        checkAll(Player.fixture) { player ->
            val saved = playerService.new(player.name)

            saved.ship.stat.base.hull.max = 100.0
            playerService update saved

            playerService.receive(player = saved, item = CHEAP_RECOVERY_DRONE)
            playerService.use(player = saved, item = CHEAP_RECOVERY_DRONE)
                .shouldNotBeNull()
                .asClue { updated ->
                    updated.ship.stat.base.hull.current shouldBe 15.0
                }
        }
    }

    expect("non existing Player should not be able to use an Item") {
        checkAll(Player.fixture) { player ->
            player.id = "non-existing-id"

            shouldThrow<NotFoundException> {
                playerService.use(player = player, item = CHEAP_RECOVERY_DRONE)
            }.asClue { exception ->
                exception.clazz shouldBe Player::class
                exception.value shouldBe player.id
            }
        }
    }

    expect("Player should not be able to use a non existing Item") {
        checkAll(Player.fixture) { player ->
            val saved = playerService.new(player.name)

            shouldThrow<NotFoundException> {
                playerService.use(player = saved, item = CHEAP_RECOVERY_DRONE)
            }.asClue { exception ->
                exception.clazz shouldBe Item::class
                exception.value shouldBe CHEAP_RECOVERY_DRONE
            }
        }
    }

    expect("Player should not be able to use Item without having it in inventory") {
        checkAll(Player.fixture) { player ->
            val saved = playerService.new(player.name)

            saved.ship.stat.base.hull.max = 100.0
            playerService update saved

            playerService.receive(player = saved, item = CHEAP_RECOVERY_DRONE)
            playerService.use(player = saved, item = CHEAP_RECOVERY_DRONE)
                .shouldNotBeNull()
                .asClue { updated ->
                    updated.ship.stat.base.hull.current shouldBe 15.0
                }

            shouldThrow<NotFoundException> {
                playerService.use(player = saved, item = CHEAP_RECOVERY_DRONE)
            }.asClue { exception ->
                exception.clazz shouldBe Item::class
                exception.value shouldBe CHEAP_RECOVERY_DRONE
            }
        }
    }
})

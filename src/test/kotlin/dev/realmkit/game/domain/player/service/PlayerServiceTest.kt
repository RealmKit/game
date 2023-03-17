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
import dev.realmkit.game.domain.player.document.Player
import dev.realmkit.hellper.extension.AssertionExtensions.shouldHaveAllErrors
import dev.realmkit.hellper.fixture.player.fixture
import dev.realmkit.hellper.infra.IntegrationTestContext
import dev.realmkit.hellper.spec.IntegrationTestSpec
import io.kotest.assertions.asClue
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.doubles.shouldBePositive
import io.kotest.matchers.doubles.shouldBeZero
import io.kotest.matchers.longs.shouldBePositive
import io.kotest.matchers.longs.shouldBeZero
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

@IntegrationTestContext
class PlayerServiceTest(
    private val playerService: PlayerService,
) : IntegrationTestSpec({
    context("integration testing PlayerService") {
        expect("all beans to be inject") {
            playerService.shouldNotBeNull()
        }

        context(".new()") {
            expect("it should create Players") {
                check(Player.fixture) { player ->
                    val saved = playerService new player.name
                    saved.id.shouldNotBeNull()
                    saved.createdAt.shouldNotBeNull()
                    saved.updatedAt.shouldNotBeNull()
                    saved.version.shouldNotBeNull()
                    saved.name.shouldNotBeNull()
                    saved.stat.base.hull.current.shouldBePositive()
                    saved.stat.base.hull.max.shouldBePositive()
                    saved.stat.base.shield.current.shouldBeZero()
                    saved.stat.base.shield.max.shouldBeZero()
                    saved.stat.base.energy.current.shouldBePositive()
                    saved.stat.base.energy.max.shouldBePositive()
                    saved.stat.base.attack.shouldBePositive()
                    saved.stat.base.defense.shouldBeZero()
                    saved.stat.base.speed.shouldBePositive()
                    saved.stat.base.aggro.shouldBePositive()
                    saved.stat.rate.shieldRegeneration.shouldBeZero()
                    saved.stat.rate.critical.shouldBeZero()
                    saved.stat.multiplier.critical.shouldBePositive()
                    saved.stat.progression.level.shouldBePositive()
                    saved.stat.progression.experience.shouldBeZero()
                    saved.resource.titanium.shouldBePositive()
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
        }

        context(".update()") {
            expect("it should level up a Player when updating it") {
                check(Player.fixture) { player ->
                    val saved = playerService new player.name
                    saved.stat.progression.level shouldBe 1
                    saved.stat.progression.experience shouldBe 0

                    saved.stat.progression.experience = 8
                    playerService update saved
                    saved.stat.progression.level shouldBe 2
                    saved.stat.progression.experience shouldBe 0
                }
            }

            expect("to thrown a NotFoundException when updating a non existing Player") {
                check(Player.fixture) { player ->
                    player.id = "non-existing-id"
                    shouldThrow<NotFoundException> {
                        playerService update player
                    }.shouldNotBeNull().asClue { exception ->
                        exception.clazz shouldBe Player::class
                        exception.value shouldBe player.id
                    }
                }
            }
        }
    }
})

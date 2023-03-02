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

import dev.realmkit.game.core.exception.ValidationException
import dev.realmkit.game.domain.player.document.Player
import dev.realmkit.hellper.extension.AssertionExtensions.shouldContainFieldError
import dev.realmkit.hellper.fixture.player.arbitrary
import dev.realmkit.hellper.infra.IntegrationTestContext
import dev.realmkit.hellper.spec.IntegrationTestSpec
import io.kotest.assertions.asClue
import io.kotest.assertions.throwables.shouldThrow
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

        expect("it should create Players") {
            check(Player.arbitrary) { player ->
                playerService new player
                player.id.shouldNotBeNull()
                player.createdAt.shouldNotBeNull()
                player.updatedAt.shouldNotBeNull()
                player.version.shouldNotBeNull()
                player.name.shouldNotBeNull()
                player.stat.progression.level.shouldNotBeNull()
                player.stat.progression.experience.shouldNotBeNull()
            }
        }

        expect("Player to gain Experience") {
            check(Player.arbitrary) { player ->
                player.stat.progression.level = 1
                player.stat.progression.experience = 0

                playerService new player
                player.stat.progression.level shouldBe 1L
                player.stat.progression.experience shouldBe 0L

                playerService gainExperience (100L to player)
                player.stat.progression.level shouldBe 1L
                player.stat.progression.experience shouldBe 100L
            }
        }

        context("Violations to be thrown") {
            expect("name should not be blank") {
                check(Player.arbitrary) { player ->
                    shouldThrow<ValidationException> {
                        playerService new player.copy(name = "")
                    }.shouldNotBeNull().asClue { problem ->
                        problem.invalid shouldContainFieldError (".name" to "must not be blank")
                    }
                }
            }
        }
    }
})

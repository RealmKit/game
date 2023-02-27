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

import dev.realmkit.game.domain.base.exception.problem.AccumulatedProblemException
import dev.realmkit.game.domain.base.exception.violation.DEFAULT_BLANK_VIOLATION_MESSAGE
import dev.realmkit.game.domain.player.document.Player
import dev.realmkit.hellper.fixture.player.arbitrary
import dev.realmkit.hellper.infra.IntegrationTestContext
import dev.realmkit.hellper.spec.IntegrationTestSpec
import io.kotest.assertions.asClue
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.nulls.shouldBeNull
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
                val persisted = playerService new player
                persisted.id.shouldNotBeNull()
                persisted.name.shouldNotBeNull()
                persisted.stat.progression.level.shouldNotBeNull()
                persisted.stat.progression.experience.shouldNotBeNull()
            }
        }

        context("Violations to be thrown") {
            expect("name should not be blank") {
                check(Player.arbitrary) { player ->
                    shouldThrow<AccumulatedProblemException> {
                        playerService new player.copy(name = "")
                    }.shouldNotBeNull().asClue { problem ->
                        problem.message shouldBe "Violations Problem"
                        problem.violations.shouldHaveSize(1)
                        problem.violations["name"].shouldNotBeNull().asClue { violation ->
                            violation.owner shouldBe Player::class.simpleName
                            violation.field shouldBe Player::name.name
                            violation.value.shouldBeNull()
                            violation.message shouldBe DEFAULT_BLANK_VIOLATION_MESSAGE
                            violation.toString() shouldBe "Player.name=<null> -> should not be blank"
                        }
                    }
                }
            }
        }
    }
})

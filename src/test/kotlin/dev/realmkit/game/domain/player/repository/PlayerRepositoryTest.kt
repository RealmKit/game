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

package dev.realmkit.game.domain.player.repository

import dev.realmkit.game.domain.player.document.Player
import dev.realmkit.hellper.fixture.arbitrary
import dev.realmkit.hellper.infra.IntegrationTestContext
import dev.realmkit.hellper.spec.IntegrationTestSpec
import io.kotest.assertions.asClue
import io.kotest.matchers.longs.shouldBeZero
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.optional.shouldBePresent
import io.kotest.matchers.shouldBe

@IntegrationTestContext
class PlayerRepositoryTest(
    private val playerRepository: PlayerRepository,
) : IntegrationTestSpec({
    context("integration testing Player Repository") {
        expect("all beans to be inject") {
            playerRepository.shouldNotBeNull()
        }

        expect("it should be empty") {
            playerRepository.run { count().shouldBeZero() }
        }

        expect("it should create $CHECK_ITERATIONS Players") {
            playerRepository.run { count().shouldBeZero() }
            check(Player.arbitrary) { player ->
                playerRepository.save(player).shouldNotBeNull()
                playerRepository.findById(player.id).shouldBePresent().asClue { find ->
                    find.id.shouldNotBeNull()
                    find.createdAt.shouldNotBeNull()
                    find.updatedAt.shouldNotBeNull()
                    find.name shouldBe player.name
                }
            }
            playerRepository.run { count().shouldBe(CHECK_ITERATIONS) }
        }
    }
})

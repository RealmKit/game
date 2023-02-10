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

package dev.realmkit.game.wrath.app

import dev.realmkit.game.envy.domain.player.document.Player
import dev.realmkit.game.envy.domain.player.repository.PlayerRepository
import dev.realmkit.test.sloth.testutils.fixture.player.arbitrary
import dev.realmkit.test.sloth.testutils.infra.IntegrationTestContext
import dev.realmkit.test.sloth.testutils.specs.IntegrationTestSpec
import io.kotest.assertions.asClue
import io.kotest.matchers.nulls.shouldNotBeNull

@IntegrationTestContext
class AppIntegrationTest(
    private val playerRepository: PlayerRepository,
) : IntegrationTestSpec({
    context("integration testing the App") {
        expect("all beans to be inject") {
            playerRepository.shouldNotBeNull()
        }

        /**
         * 1. create a player
         *    - spec
         *    - name
         * 2. player fighting enemy
         *    - player deals damage to enemy
         *    - enemy deals damage to player
         *    - enemy dies
         *    - player loots and levels
         */
        expect("it should play a game") {
            check(Player.arbitrary) { player ->
                collect(player.specialization.type)

                playerRepository.save(player)
                player.shouldNotBeNull()
                    .asClue {
                        it.id.shouldNotBeNull()
                        it.name.shouldNotBeNull()
                    }
            }
        }
    }
})

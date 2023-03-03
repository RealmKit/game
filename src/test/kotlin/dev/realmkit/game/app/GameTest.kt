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

package dev.realmkit.game.app

import dev.realmkit.game.domain.player.document.Player
import dev.realmkit.game.domain.player.service.PlayerService
import dev.realmkit.hellper.extension.FakerExtensions.fake
import dev.realmkit.hellper.infra.IntegrationTestContext
import dev.realmkit.hellper.spec.IntegrationTestSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotBeEmpty

@IntegrationTestContext
class GameTest(
    private val playerService: PlayerService,
) : IntegrationTestSpec({
    context("integration testing Game Application") {
        expect("all beans to be inject") {
            playerService.shouldNotBeNull()
        }

        expect("the game to run normally") {
            // Creates a new Player
            val player = playerService new Player(name = fake.superhero.name())
            player.id.shouldNotBeNull()
            player.name.shouldNotBeNull().shouldNotBeEmpty()
            player.stat.progression.level shouldBe 1L
            player.stat.progression.experience shouldBe 0L

            // Gain Experience
            val playerAfterGainXp = playerService gainExperience (100L to player)
            playerAfterGainXp.stat.progression.level shouldBe 1L
            playerAfterGainXp.stat.progression.experience shouldBe 100L
        }
    }
})

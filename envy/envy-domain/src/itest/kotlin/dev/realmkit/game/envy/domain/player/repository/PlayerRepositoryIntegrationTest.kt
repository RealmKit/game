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

package dev.realmkit.game.envy.domain.player.repository

import dev.realmkit.game.envy.domain.player.document.Player
import dev.realmkit.test.sloth.testutils.fixture.player.arbitrary
import dev.realmkit.test.sloth.testutils.infra.IntegrationTestContext
import dev.realmkit.test.sloth.testutils.specs.IntegrationTestSpec
import io.kotest.assertions.asClue
import io.kotest.matchers.longs.shouldBeZero
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.optional.shouldBePresent
import io.kotest.matchers.shouldBe

@IntegrationTestContext
class PlayerRepositoryIntegrationTest(
    private val playerRepository: PlayerRepository,
) : IntegrationTestSpec({
    expect("all beans to be inject") {
        playerRepository.shouldNotBeNull()
    }

    expect("it should be empty") {
        context {
            playerRepository.count().shouldBeZero()
        }
    }

    expect("it should create a new Player") {
        context {
            playerRepository.count().shouldBeZero()
        }

        check(Player.arbitrary) { player ->
            playerRepository.save(player).shouldNotBeNull()
            playerRepository.findById(player.id)
                .shouldBePresent()
                .asClue {
                    it.id shouldBe player.id
                    it.createdDate.shouldNotBeNull()
                    it.updatedDate.shouldNotBeNull()
                    it.name shouldBe player.name
                    it.stat.progression.experience.shouldBeZero()
                    it.stat.progression.level.shouldBeZero()
                    it.stat.base.attack.shouldBeZero()
                    it.stat.base.speed.shouldBeZero()
                    it.stat.base.health.current.shouldBeZero()
                    it.stat.base.health.max.shouldBeZero()
                    it.stat.base.mana.current.shouldBeZero()
                    it.stat.base.mana.max.shouldBeZero()
                    it.stat.base.stamina.current.shouldBeZero()
                    it.stat.base.stamina.max.shouldBeZero()
                    it.currency.gold.shouldBeZero()
                    it.currency.gem.shouldBeZero()
                    it.equipmentSlot.weapon.shouldBeNull()
                    it.equipmentSlot.armor.shouldBeNull()
                    it.equipmentSlot.ring.shouldBeNull()
                }
        }
    }
})

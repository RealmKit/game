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

package dev.realmkit.game.domain.item.service

import dev.realmkit.game.domain.base.extension.MongoRepositoryExtensions.persist
import dev.realmkit.game.domain.player.document.Player
import dev.realmkit.game.domain.player.repository.PlayerRepository
import dev.realmkit.game.domain.staticdata.enums.StaticDataItemEnum
import dev.realmkit.game.domain.staticdata.enums.StaticDataItemEnum.CHEAP_RECOVERY_DRONE
import dev.realmkit.hellper.extension.AssertionExtensions.exhaustive
import dev.realmkit.hellper.extension.FakerExtensions.faker
import dev.realmkit.hellper.fixture.player.PlayerFixture.fixture
import dev.realmkit.hellper.infra.IntegrationTestContext
import dev.realmkit.hellper.spec.IntegrationTestSpec
import io.kotest.assertions.asClue
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotBeBlank
import io.kotest.property.checkAll

@IntegrationTestContext
class ItemServiceTest(
    private val itemService: ItemService,
    private val playerRepository: PlayerRepository,
) : IntegrationTestSpec({
    expect("all beans to be inject") {
        itemService.shouldNotBeNull()
    }

    expect("to persist items from StaticDataItemEnum") {
        checkAll(exhaustive<StaticDataItemEnum>(), Player.fixture) { enum, player ->
            player.id = faker.random.nextUUID()
            itemService.new(player to enum)
                .shouldNotBeNull()
                .asClue { item ->
                    item.id.shouldNotBeNull()
                    item.createdAt.shouldNotBeNull()
                    item.updatedAt.shouldNotBeNull()
                    item.version.shouldNotBeNull()
                    item.owner shouldBe player.id
                    item.name.shouldNotBeBlank()
                    item.stat.shouldNotBeNull()
                    item.stat.base.shouldNotBeNull()
                }
        }
    }

    expect("to get items from StaticDataItemEnum") {
        checkAll(exhaustive<StaticDataItemEnum>()) { enum ->
            itemService[enum]
                .shouldNotBeNull()
                .asClue { item ->
                    item.owner.shouldBeNull()
                    item.name.shouldNotBeBlank()
                    item.stat.shouldNotBeNull()
                    item.stat.base.shouldNotBeNull()
                }
        }
    }

    expect("Player to use an Item to recovery Hull") {
        checkAll(Player.fixture) { player ->
            player.ship.stat.base.hull.max = 9.0
            player.ship.stat.base.hull.current = 0.0
            playerRepository persist player

            itemService.new(player to CHEAP_RECOVERY_DRONE)
            itemService.use(player to CHEAP_RECOVERY_DRONE)

            player.ship.stat.base.hull.current shouldBe 9.0
        }
    }

    expect("Player to use all Items") {
        checkAll(exhaustive<StaticDataItemEnum>(), Player.fixture) { enum, player ->
            playerRepository persist player

            itemService.new(player to enum)
            itemService.use(player to enum)
        }
    }
})

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

package dev.realmkit.game.domain.staticdata.property

import dev.realmkit.game.domain.staticdata.enums.StaticDataItemEnum.DRONE_RECOVERY_V1
import dev.realmkit.game.domain.staticdata.enums.StaticDataShipEnum.BATTLE_WAR_SHIP_V1
import dev.realmkit.hellper.infra.IntegrationTestContext
import dev.realmkit.hellper.spec.IntegrationTestSpec
import io.kotest.assertions.asClue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

@IntegrationTestContext
class StaticDataPropertiesTest(
    private val staticDataProperties: StaticDataProperties,
) : IntegrationTestSpec({
    expect("all beans to be inject") {
        staticDataProperties.shouldNotBeNull()
    }

    expect("turnDuration to generate the turnDuration StaticData values from properties") {
        staticDataProperties.battleDuration()
            .shouldNotBeNull()
            .shouldBe(10L)
    }

    expect("resource to generate the resource StaticData values from properties") {
        staticDataProperties.resource()
            .shouldNotBeNull()
            .asClue { resource ->
                resource.titanium shouldBe 1_000L
                resource.crystal shouldBe 0L
                resource.darkMatter shouldBe 0L
                resource.antiMatter shouldBe 0L
                resource.purunhalium shouldBe 0L
            }
    }

    expect("ships to have BATTLE_WAR_SHIP_V1 ship") {
        staticDataProperties.ships(BATTLE_WAR_SHIP_V1)
            .shouldNotBeNull()
            .asClue { ship ->
                ship.name shouldBe "WarShip V1.1"
                ship.stat.shouldNotBeNull()
                ship.stat.base.shouldNotBeNull()
                ship.stat.base.hull.max shouldBe 5.0
                ship.stat.base.hull.current shouldBe 5.0
                ship.stat.base.shield.max shouldBe 0.0
                ship.stat.base.shield.current shouldBe 0.0
                ship.stat.base.energy.max shouldBe 5.0
                ship.stat.base.energy.current shouldBe 5.0
                ship.stat.base.attack shouldBe 1.0
                ship.stat.base.defense shouldBe 0.0
                ship.stat.base.speed shouldBe 1.0
                ship.stat.base.aggro shouldBe 1.0
                ship.stat.rate.shouldNotBeNull()
                ship.stat.rate.shieldRegeneration shouldBe 0.0
                ship.stat.rate.critical shouldBe 0.0
                ship.stat.multiplier.shouldNotBeNull()
                ship.stat.multiplier.critical shouldBe 1.0
                ship.stat.progression.level shouldBe 1L
                ship.stat.progression.experience shouldBe 0L
            }
    }

    expect("items to have DRONE_RECOVERY_V1 item") {
        staticDataProperties.items(DRONE_RECOVERY_V1)
            .shouldNotBeNull()
            .asClue { item ->
                item.name shouldBe "Drone Recovery V1"
                item.stat.shouldNotBeNull()
                item.stat.base.shouldNotBeNull()
                item.stat.base.hull.current shouldBe 10.0
            }
    }
})

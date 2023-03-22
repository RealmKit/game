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

package dev.realmkit.game.domain.ship.service

import dev.realmkit.game.domain.staticdata.enums.StaticDataShipEnum.BATTLE_WAR_SHIP_V1
import dev.realmkit.hellper.infra.IntegrationTestContext
import dev.realmkit.hellper.spec.IntegrationTestSpec
import io.kotest.assertions.asClue
import io.kotest.matchers.doubles.shouldBeZero
import io.kotest.matchers.longs.shouldBeZero
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

@IntegrationTestContext
class ShipServiceTest(
    private val shipService: ShipService,
) : IntegrationTestSpec({
    expect("all beans to be inject") {
        shipService.shouldNotBeNull()
    }

    expect("battleWarShipV1 to generate the battleWarShipV1 StaticData values from properties") {
        shipService[BATTLE_WAR_SHIP_V1]
            .shouldNotBeNull()
            .asClue { ship ->
                ship.name shouldBe "WarShip V1.1"
                ship.stat.shouldNotBeNull()
                ship.stat.base.shouldNotBeNull()
                ship.stat.base.hull.max shouldBe 5.0
                ship.stat.base.hull.current shouldBe 5.0
                ship.stat.base.shield.max.shouldBeZero()
                ship.stat.base.shield.current.shouldBeZero()
                ship.stat.base.energy.max shouldBe 5.0
                ship.stat.base.energy.current shouldBe 5.0
                ship.stat.base.attack shouldBe 1.0
                ship.stat.base.defense.shouldBeZero()
                ship.stat.base.speed shouldBe 1.0
                ship.stat.base.aggro shouldBe 1.0
                ship.stat.rate.shouldNotBeNull()
                ship.stat.rate.shieldRegeneration.shouldBeZero()
                ship.stat.rate.critical.shouldBeZero()
                ship.stat.multiplier.shouldNotBeNull()
                ship.stat.multiplier.critical shouldBe 1.0
                ship.stat.progression.level shouldBe 1
                ship.stat.progression.experience.shouldBeZero()
                ship.stat.progression.points.shouldBeZero()
            }
    }
})

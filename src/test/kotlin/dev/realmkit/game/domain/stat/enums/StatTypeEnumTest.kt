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

package dev.realmkit.game.domain.stat.enums

import dev.realmkit.game.domain.stat.document.Stat
import dev.realmkit.game.domain.stat.enums.StatTypeEnum.STAT_BASE_AGGRO
import dev.realmkit.game.domain.stat.enums.StatTypeEnum.STAT_BASE_ATTACK
import dev.realmkit.game.domain.stat.enums.StatTypeEnum.STAT_BASE_DEFENSE
import dev.realmkit.game.domain.stat.enums.StatTypeEnum.STAT_BASE_ENERGY
import dev.realmkit.game.domain.stat.enums.StatTypeEnum.STAT_BASE_HULL
import dev.realmkit.game.domain.stat.enums.StatTypeEnum.STAT_BASE_SHIELD
import dev.realmkit.game.domain.stat.enums.StatTypeEnum.STAT_BASE_SPEED
import dev.realmkit.game.domain.stat.enums.StatTypeEnum.STAT_MULTIPLIER_CRITICAL
import dev.realmkit.game.domain.stat.enums.StatTypeEnum.STAT_RATE_CRITICAL
import dev.realmkit.game.domain.stat.enums.StatTypeEnum.STAT_RATE_SHIELD_REGENERATION
import dev.realmkit.hellper.fixture.stat.StatFixture.fixture
import dev.realmkit.hellper.spec.TestSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.property.checkAll

class StatTypeEnumTest : TestSpec({
    expect("should have all values") {
        StatTypeEnum.values()
            .shouldHaveSize(10)
            .shouldContainExactly(
                STAT_BASE_HULL,
                STAT_BASE_SHIELD,
                STAT_BASE_ENERGY,
                STAT_BASE_ATTACK,
                STAT_BASE_DEFENSE,
                STAT_BASE_SPEED,
                STAT_BASE_AGGRO,
                STAT_RATE_SHIELD_REGENERATION,
                STAT_RATE_CRITICAL,
                STAT_MULTIPLIER_CRITICAL,
            )
    }

    expect("buy STAT_BASE_HULL") {
        checkAll(Stat.fixture) { stat ->
            stat.base.hull.max = 0.0
            STAT_BASE_HULL.buy(stat, 10)
            stat.base.hull.max shouldBe 100.0
        }
    }

    expect("buy STAT_BASE_SHIELD") {
        checkAll(Stat.fixture) { stat ->
            stat.base.shield.max = 0.0
            STAT_BASE_SHIELD.buy(stat, 10)
            stat.base.shield.max shouldBe 50.0
        }
    }

    expect("buy STAT_BASE_ENERGY") {
        checkAll(Stat.fixture) { stat ->
            stat.base.energy.max = 0.0
            STAT_BASE_ENERGY.buy(stat, 10)
            stat.base.energy.max shouldBe 50.0
        }
    }

    expect("buy STAT_BASE_ATTACK") {
        checkAll(Stat.fixture) { stat ->
            stat.base.attack = 0.0
            STAT_BASE_ATTACK.buy(stat, 10)
            stat.base.attack shouldBe 10.0
        }
    }

    expect("buy STAT_BASE_DEFENSE") {
        checkAll(Stat.fixture) { stat ->
            stat.base.defense = 0.0
            STAT_BASE_DEFENSE.buy(stat, 10)
            stat.base.defense shouldBe 10.0
        }
    }

    expect("buy STAT_BASE_SPEED") {
        checkAll(Stat.fixture) { stat ->
            stat.base.speed = 0.0
            STAT_BASE_SPEED.buy(stat, 10)
            stat.base.speed shouldBe 10.0
        }
    }

    expect("buy STAT_BASE_AGGRO") {
        checkAll(Stat.fixture) { stat ->
            stat.base.aggro = 0.0
            STAT_BASE_AGGRO.buy(stat, 10)
            stat.base.aggro shouldBe 10.0
        }
    }

    expect("buy STAT_RATE_SHIELD_REGENERATION") {
        checkAll(Stat.fixture) { stat ->
            stat.rate.shieldRegeneration = 0.0
            STAT_RATE_SHIELD_REGENERATION.buy(stat, 10)
            stat.rate.shieldRegeneration shouldBe 0.1
        }
    }

    expect("buy STAT_RATE_CRITICAL") {
        checkAll(Stat.fixture) { stat ->
            stat.rate.critical = 0.0
            STAT_RATE_CRITICAL.buy(stat, 10)
            stat.rate.critical shouldBe 0.1
        }
    }

    expect("buy STAT_MULTIPLIER_CRITICAL") {
        checkAll(Stat.fixture) { stat ->
            stat.multiplier.critical = 0.0
            STAT_MULTIPLIER_CRITICAL.buy(stat, 10)
            stat.multiplier.critical shouldBe 0.1
        }
    }
})

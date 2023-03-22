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

package dev.realmkit.game.domain.stat.service

import dev.realmkit.game.core.exception.NotEnoughPointsException
import dev.realmkit.game.domain.stat.document.Stat
import dev.realmkit.game.domain.stat.enums.StatTypeEnum
import dev.realmkit.game.domain.stat.enums.StatTypeEnum.STAT_BASE_AGGRO
import dev.realmkit.game.domain.stat.enums.StatTypeEnum.STAT_BASE_HULL
import dev.realmkit.hellper.fixture.stat.StatFixture.fixture
import dev.realmkit.hellper.infra.IntegrationTestContext
import dev.realmkit.hellper.spec.IntegrationTestSpec
import io.kotest.assertions.asClue
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.longs.shouldBeZero
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.enum
import io.kotest.property.checkAll

@IntegrationTestContext
class StatServiceTest(
    private val statService: StatService,
) : IntegrationTestSpec({
    expect("all beans to be inject") {
        statService.shouldNotBeNull()
    }

    expect("to not level up a Stat") {
        checkAll(Stat.fixture) { stat ->
            stat.progression.level = 1
            stat.progression.experience = 0
            stat.progression.points = 0

            statService levelUp stat
            stat.progression.level shouldBe 1
            stat.progression.experience.shouldBeZero()
            stat.progression.points.shouldBeZero()
        }
    }

    expect("to level up a Stat from level 1 to level 2") {
        checkAll(Stat.fixture) { stat ->
            stat.progression.level = 1
            stat.progression.experience = 8
            stat.progression.points = 0

            statService levelUp stat
            stat.progression.level shouldBe 2
            stat.progression.experience.shouldBeZero()
            stat.progression.points shouldBe 5
        }
    }

    expect("to level up a Stat from level 1 to level 3") {
        checkAll(Stat.fixture) { stat ->
            stat.progression.level = 1
            stat.progression.experience = 8
            stat.progression.points = 0

            statService levelUp stat
            stat.progression.level shouldBe 2
            stat.progression.experience.shouldBeZero()
            stat.progression.points shouldBe 5
            stat.progression.experience = 27

            statService levelUp stat
            stat.progression.level shouldBe 3
            stat.progression.experience.shouldBeZero()
            stat.progression.points shouldBe 10
        }
    }

    expect("to level up a Stat from level 1 to level 100") {
        checkAll(Stat.fixture) { stat ->
            stat.progression.level = 1
            stat.progression.experience = 25_502_500
            stat.progression.points = 0

            statService levelUp stat
            stat.progression.level shouldBe 100
            stat.progression.experience shouldBe 1
            stat.progression.points shouldBe 495
        }
    }

    expect("to level up to level 2 and buy 5 HULL points") {
        checkAll(Stat.fixture) { stat ->
            stat.base.hull.max = 10.0
            stat.progression.level = 1
            stat.progression.experience = 8
            stat.progression.points = 0

            statService levelUp stat
            stat.base.hull.max shouldBe 10.0
            stat.progression.level shouldBe 2
            stat.progression.experience.shouldBeZero()
            stat.progression.points shouldBe 5

            statService.buy(stat = stat, type = STAT_BASE_HULL, points = 5)
            stat.base.hull.max shouldBe 60.0
            stat.progression.level shouldBe 2
            stat.progression.experience.shouldBeZero()
            stat.progression.points.shouldBeZero()
        }
    }

    expect("to level up to level 2 and buy 5 <*> points") {
        checkAll(Arb.enum<StatTypeEnum>(), Stat.fixture) { enum, stat ->
            stat.progression.level = 1
            stat.progression.experience = 8
            stat.progression.points = 0

            statService levelUp stat
            stat.progression.level shouldBe 2
            stat.progression.experience.shouldBeZero()
            stat.progression.points shouldBe 5

            statService.buy(stat = stat, type = enum, points = 5)
            stat.progression.level shouldBe 2
            stat.progression.experience.shouldBeZero()
            stat.progression.points.shouldBeZero()
        }
    }

    expect("to fail to buy stat points") {
        checkAll(Stat.fixture) { stat ->
            stat.progression.points = 1
            shouldThrow<NotEnoughPointsException> {
                statService.buy(stat = stat, type = STAT_BASE_AGGRO, points = 2)
            }.asClue { exception ->
                exception.available shouldBe 1
                exception.points shouldBe 2
            }
        }
    }
})

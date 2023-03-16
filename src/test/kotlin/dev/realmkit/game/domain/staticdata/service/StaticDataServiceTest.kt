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

package dev.realmkit.game.domain.staticdata.service

import dev.realmkit.hellper.infra.IntegrationTestContext
import dev.realmkit.hellper.spec.IntegrationTestSpec
import io.kotest.assertions.asClue
import io.kotest.matchers.doubles.shouldBePositive
import io.kotest.matchers.doubles.shouldBeZero
import io.kotest.matchers.longs.shouldBePositive
import io.kotest.matchers.longs.shouldBeZero
import io.kotest.matchers.nulls.shouldNotBeNull

@IntegrationTestContext
class StaticDataServiceTest(
    private val staticDataService: StaticDataService,
) : IntegrationTestSpec({
    context("integration testing StaticDataService") {
        expect("all beans to be inject") {
            staticDataService.shouldNotBeNull()
        }

        context(".initial()") {
            expect("initial to generate the init StaticData values from properties") {
                staticDataService.initial()
                    .shouldNotBeNull()
                    .asClue { staticData ->
                        staticData.stat.shouldNotBeNull()
                        staticData.stat.base.shouldNotBeNull()
                        staticData.stat.base.hull.current.shouldBePositive()
                        staticData.stat.base.hull.max.shouldBePositive()
                        staticData.stat.base.shield.current.shouldBeZero()
                        staticData.stat.base.shield.max.shouldBeZero()
                        staticData.stat.base.power.shouldBePositive()
                        staticData.stat.base.defense.shouldBeZero()
                        staticData.stat.base.speed.shouldBePositive()
                        staticData.stat.base.aggro.shouldBePositive()
                        staticData.stat.rate.shouldNotBeNull()
                        staticData.stat.rate.shieldRegeneration.shouldBeZero()
                        staticData.stat.rate.critical.shouldBeZero()
                        staticData.stat.multiplier.shouldNotBeNull()
                        staticData.stat.multiplier.critical.shouldBePositive()
                        staticData.stat.progression.shouldNotBeNull()
                        staticData.stat.progression.level.shouldBePositive()
                        staticData.stat.progression.experience.shouldBeZero()
                    }
            }
        }

        context(".battle()") {
            expect("battle to generate the battle StaticData values from properties") {
                staticDataService.battle()
                    .shouldNotBeNull()
                    .asClue { staticData ->
                        staticData.battleDuration.shouldBePositive()
                        staticData.turnDuration.shouldBePositive()
                    }
            }
        }
    }
})

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

package dev.realmkit.game.domain.staticdata.document

import dev.realmkit.game.domain.aliases.CurrentMaxDouble
import dev.realmkit.game.domain.resource.document.Resource
import dev.realmkit.game.domain.stat.document.Stat
import dev.realmkit.game.domain.stat.document.StatBase
import dev.realmkit.game.domain.stat.document.StatMultiplier
import dev.realmkit.game.domain.stat.document.StatProgression
import dev.realmkit.game.domain.stat.document.StatRate
import dev.realmkit.hellper.spec.TestSpec
import io.kotest.assertions.asClue
import io.kotest.matchers.doubles.shouldBePositive
import io.kotest.matchers.longs.shouldBePositive
import io.kotest.matchers.nulls.shouldNotBeNull

class StaticDataStatTest : TestSpec({
    context("unit testing StaticData") {
        context("instantiate") {
            expect("instantiate a StaticData") {
                StaticDataValues(
                    stat = Stat(
                        base = StatBase(
                            hull = CurrentMaxDouble(max = 100.0),
                            shield = CurrentMaxDouble(max = 100.0),
                            energy = CurrentMaxDouble(max = 100.0),
                            attack = 100.0,
                            defense = 100.0,
                            speed = 100.0,
                            aggro = 100.0,
                        ),
                        rate = StatRate(
                            shieldRegeneration = 100.0,
                            critical = 100.0,
                        ),
                        multiplier = StatMultiplier(
                            critical = 100.0,
                        ),
                        progression = StatProgression(
                            level = 1,
                            experience = 1,
                        ),
                    ),
                    resource = Resource(
                        titanium = 100,
                        crystal = 100,
                        darkMatter = 100,
                        antiMatter = 100,
                        purunhalium = 100,
                    ),
                ).shouldNotBeNull().asClue { staticData ->
                    staticData.stat.shouldNotBeNull()
                    staticData.stat.base.hull.current.shouldBePositive()
                    staticData.stat.base.hull.max.shouldBePositive()
                    staticData.stat.base.shield.current.shouldBePositive()
                    staticData.stat.base.shield.max.shouldBePositive()
                    staticData.stat.base.energy.current.shouldBePositive()
                    staticData.stat.base.energy.max.shouldBePositive()
                    staticData.stat.base.attack.shouldBePositive()
                    staticData.stat.base.defense.shouldBePositive()
                    staticData.stat.base.speed.shouldBePositive()
                    staticData.stat.base.aggro.shouldBePositive()
                    staticData.stat.rate.shieldRegeneration.shouldBePositive()
                    staticData.stat.rate.critical.shouldBePositive()
                    staticData.stat.multiplier.critical.shouldBePositive()
                    staticData.stat.progression.level.shouldBePositive()
                    staticData.stat.progression.experience.shouldBePositive()
                    staticData.resource.titanium.shouldBePositive()
                    staticData.resource.crystal.shouldBePositive()
                    staticData.resource.darkMatter.shouldBePositive()
                    staticData.resource.antiMatter.shouldBePositive()
                    staticData.resource.purunhalium.shouldBePositive()
                }
            }
        }
    }
})

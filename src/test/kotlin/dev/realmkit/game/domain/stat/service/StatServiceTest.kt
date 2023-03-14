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

import dev.realmkit.game.domain.stat.document.Stat
import dev.realmkit.hellper.fixture.stat.fixture
import dev.realmkit.hellper.infra.IntegrationTestContext
import dev.realmkit.hellper.spec.IntegrationTestSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.property.arbitrary.arbitrary

@IntegrationTestContext
class StatServiceTest(
    private val statService: StatService,
) : IntegrationTestSpec({
    context("integration testing StatService") {
        expect("all beans to be inject") {
            statService.shouldNotBeNull()
        }

        expect("to not level up a Stat") {
            check(arbitrary { Stat.fixture }) { stat ->
                stat.progression.level = 1L
                stat.progression.experience = 0L

                statService levelUp stat
                stat.progression.level shouldBe 1L
                stat.progression.experience shouldBe 0L
            }
        }

        expect("to level up a Stat from level 1 to level 2") {
            check(arbitrary { Stat.fixture }) { stat ->
                stat.progression.level = 1
                stat.progression.experience = 8

                statService levelUp stat
                stat.progression.level shouldBe 2
                stat.progression.experience shouldBe 0
            }
        }

        expect("to level up a Stat from level 1 to level 3") {
            check(arbitrary { Stat.fixture }) { stat ->
                stat.progression.level = 1
                stat.progression.experience = 8

                statService levelUp stat
                stat.progression.level shouldBe 2
                stat.progression.experience shouldBe 0
                stat.progression.experience = 27

                statService levelUp stat
                stat.progression.level shouldBe 3
                stat.progression.experience shouldBe 0
            }
        }

        expect("to level up a Stat from level 1 to level 100") {
            check(arbitrary { Stat.fixture }) { stat ->
                stat.progression.level = 1L
                stat.progression.experience = 25_502_500L

                statService levelUp stat
                stat.progression.level shouldBe 100L
                stat.progression.experience shouldBe 1L
            }
        }
    }
})

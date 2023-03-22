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

import dev.realmkit.game.domain.staticdata.enums.StaticDataItemEnum
import dev.realmkit.game.domain.staticdata.enums.StaticDataShipEnum
import dev.realmkit.hellper.infra.IntegrationTestContext
import dev.realmkit.hellper.spec.IntegrationTestSpec
import io.kotest.assertions.asClue
import io.kotest.matchers.longs.shouldBeZero
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotBeBlank
import io.kotest.property.Arb
import io.kotest.property.arbitrary.enum
import io.kotest.property.checkAll

@IntegrationTestContext
class StaticDataPropertiesTest(
    private val staticDataProperties: StaticDataProperties,
) : IntegrationTestSpec({
    expect("all beans to be inject") {
        staticDataProperties.shouldNotBeNull()
    }

    expect("turnDuration to generate the turnDuration StaticData values from properties") {
        staticDataProperties.config()
            .shouldNotBeNull()
            .asClue { config ->
                config.battleDuration shouldBe 10
                config.turnDuration shouldBe 10
                config.pointsPerLevel shouldBe 5
            }
    }

    expect("resource to generate the resource StaticData values from properties") {
        staticDataProperties.resource()
            .shouldNotBeNull()
            .asClue { resource ->
                resource.titanium shouldBe 1_000
                resource.crystal.shouldBeZero()
                resource.darkMatter.shouldBeZero()
                resource.antiMatter.shouldBeZero()
                resource.purunhalium.shouldBeZero()
            }
    }

    expect("to get items from StaticDataShipEnum") {
        checkAll(Arb.enum<StaticDataShipEnum>()) { enum ->
            staticDataProperties.ships(enum)
                .shouldNotBeNull()
                .asClue { item ->
                    item.name.shouldNotBeBlank()
                    item.stat.shouldNotBeNull()
                }
        }
    }

    expect("to get items from StaticDataItemEnum") {
        checkAll(Arb.enum<StaticDataItemEnum>()) { enum ->
            staticDataProperties.items(enum)
                .shouldNotBeNull()
                .asClue { item ->
                    item.owner.shouldBeNull()
                    item.name.shouldNotBeBlank()
                    item.stat.shouldNotBeNull()
                }
        }
    }
})

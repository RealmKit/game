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

import dev.realmkit.game.domain.staticdata.enums.StaticDataItemEnum.CHEAP_RECOVERY_DRONE
import dev.realmkit.hellper.infra.IntegrationTestContext
import dev.realmkit.hellper.spec.IntegrationTestSpec
import io.kotest.assertions.asClue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

@IntegrationTestContext
class ItemServiceTest(
    private val itemService: ItemService,
) : IntegrationTestSpec({
    expect("all beans to be inject") {
        itemService.shouldNotBeNull()
    }

    expect("battleWarItemV1 to generate the battleWarItemV1 StaticData values from properties") {
        itemService[CHEAP_RECOVERY_DRONE]
            .shouldNotBeNull()
            .asClue { item ->
                item.name shouldBe "Cheap Recovery Drone"
                item.stat.shouldNotBeNull()
                item.stat.base.shouldNotBeNull()
                item.stat.base.hull.current shouldBe 10.0
            }
    }
})

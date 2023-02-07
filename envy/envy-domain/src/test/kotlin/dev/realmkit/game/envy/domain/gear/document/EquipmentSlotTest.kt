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

package dev.realmkit.game.envy.domain.gear.document

import dev.realmkit.game.envy.domain.gear.enums.GearType
import dev.realmkit.game.envy.domain.stat.property.BaseStatsProperties
import dev.realmkit.test.sloth.testutils.fixture.gear.arbitrary
import dev.realmkit.test.sloth.testutils.specs.TestSpec
import io.kotest.assertions.asClue
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

class EquipmentSlotTest : TestSpec({
    context("unit testing EquipmentSlot") {
        expect("EquipmentSlot have nothing inside by default") {
            val equipmentSlot = EquipmentSlot()
            equipmentSlot.armor.shouldBeNull()
            equipmentSlot.ring.shouldBeNull()
            equipmentSlot.weapon.shouldBeNull()
        }

        expect("to create a EquipmentSlot with all slots filled") {
            check(EquipmentSlot.arbitrary) { equipmentsSlot ->
                equipmentsSlot.armor.shouldNotBeNull()
                    .asClue {
                        it.name.shouldNotBeNull()
                        it.type shouldBe GearType.ARMOR
                        it.stat shouldBe BaseStatsProperties.stat
                    }
                equipmentsSlot.ring.shouldNotBeNull()
                    .asClue {
                        it.name.shouldNotBeNull()
                        it.type shouldBe GearType.RING
                        it.stat shouldBe BaseStatsProperties.stat
                    }
                equipmentsSlot.weapon.shouldNotBeNull()
                    .asClue {
                        it.name.shouldNotBeNull()
                        it.type shouldBe GearType.WEAPON
                        it.stat shouldBe BaseStatsProperties.stat
                    }
            }
        }
    }
})

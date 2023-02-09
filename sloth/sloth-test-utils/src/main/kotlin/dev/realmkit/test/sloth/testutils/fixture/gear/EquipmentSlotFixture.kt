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

package dev.realmkit.test.sloth.testutils.fixture.gear

import dev.realmkit.game.envy.domain.gear.document.EquipmentSlot
import dev.realmkit.game.envy.domain.gear.document.slot.ArmorGear
import dev.realmkit.game.envy.domain.gear.document.slot.RingGear
import dev.realmkit.game.envy.domain.gear.document.slot.WeaponGear
import dev.realmkit.test.sloth.testutils.fixture.gear.slot.arbitrary
import dev.realmkit.test.sloth.testutils.fixture.gear.slot.fixture
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary

val EquipmentSlot.Companion.fixture: EquipmentSlot
    get() = fixture()

val EquipmentSlot.Companion.arbitrary: Arb<EquipmentSlot>
    get() = arbitrary {
        fixture(
            armor = ArmorGear.arbitrary.bind(),
            ring = RingGear.arbitrary.bind(),
            weapon = WeaponGear.arbitrary.bind(),
        )
    }

/**
 * @param armor
 * @param ring
 * @param weapon
 */
fun EquipmentSlot.Companion.fixture(
    armor: ArmorGear = ArmorGear.fixture,
    ring: RingGear = RingGear.fixture,
    weapon: WeaponGear = WeaponGear.fixture,
): EquipmentSlot = EquipmentSlot(
    armor = armor,
    ring = ring,
    weapon = weapon,
)

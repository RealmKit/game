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

package dev.realmkit.game.envy.domain.player.document

import dev.realmkit.game.envy.domain.base.document.BaseDocument
import dev.realmkit.game.envy.domain.currency.document.Currency
import dev.realmkit.game.envy.domain.gear.document.EquipmentSlot
import dev.realmkit.game.envy.domain.specialization.document.NewbieSpecialization
import dev.realmkit.game.envy.domain.specialization.document.Specialization
import org.springframework.data.mongodb.core.mapping.Document

/**
 * [Player]
 * The [Player] entity, stores the name, attributes, currencies and equipments
 *
 * @property name
 * @property currency
 * @property equipmentSlot
 * @property specialization
 */
@Document
class Player(
    val name: String,
    val specialization: Specialization = NewbieSpecialization(),
    val currency: Currency = Currency(),
    val equipmentSlot: EquipmentSlot = EquipmentSlot(),
) : BaseDocument() {
    companion object
}

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

package dev.realmkit.game.domain.stat.extension.operator

import dev.realmkit.game.core.extension.operator.CurrentMaxOperator.minus
import dev.realmkit.game.core.extension.operator.CurrentMaxOperator.plus
import dev.realmkit.game.domain.stat.document.StatBase

/**
 * # [StatBaseOperator]
 * [StatBase] operators
 */
object StatBaseOperator {
    /**
     * ## [plus]
     * [StatBase] `+` operator, sum the properties
     *
     * @see [StatBase]
     *
     * @param other the other [StatBase]
     * @return a copy of [StatBase] with the summed properties
     */
    operator fun StatBase.plus(other: StatBase): StatBase =
        copy(
            hull = hull + other.hull,
            shield = shield + other.shield,
            energy = energy + other.energy,
            attack = attack + other.attack,
            defense = defense + other.defense,
            speed = speed + other.speed,
            aggro = aggro + other.aggro,
        )

    /**
     * ## [minus]
     * [StatBase] `-` operator, subtract the properties
     *
     * @see [StatBase]
     *
     * @param other the other [StatBase]
     * @return a copy of [StatBase] with the subtracted properties
     */
    operator fun StatBase.minus(other: StatBase): StatBase =
        copy(
            hull = hull - other.hull,
            shield = shield - other.shield,
            energy = energy - other.energy,
            attack = attack - other.attack,
            defense = defense - other.defense,
            speed = speed - other.speed,
            aggro = aggro - other.aggro,
        )
}

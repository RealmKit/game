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
import dev.realmkit.game.domain.stat.document.Stat
import dev.realmkit.game.domain.stat.extension.operator.StatBaseOperator.minus
import dev.realmkit.game.domain.stat.extension.operator.StatBaseOperator.plus
import dev.realmkit.game.domain.stat.extension.operator.StatMultiplierOperator.minus
import dev.realmkit.game.domain.stat.extension.operator.StatMultiplierOperator.plus
import dev.realmkit.game.domain.stat.extension.operator.StatProgressionOperator.plus
import dev.realmkit.game.domain.stat.extension.operator.StatRateOperator.minus
import dev.realmkit.game.domain.stat.extension.operator.StatRateOperator.plus

/**
 * # [StatOperator]
 * [Stat] operators
 */
object StatOperator {
    /**
     * ## [plus]
     * [Stat] `+` operator, sum the properties
     *
     * @see [Stat]
     *
     * @param other the other [Stat]
     * @return a copy of [Stat] with the summed properties
     */
    operator fun Stat.plus(other: Stat): Stat =
        copy(
            base = base + other.base,
            rate = rate + other.rate,
            multiplier = multiplier + other.multiplier,
            progression = progression + other.progression,
        )

    /**
     * ## [minus]
     * [Stat] `-` operator, subtract the properties
     *
     * @see [Stat]
     *
     * @param other the other [Stat]
     * @return a copy of [Stat] with the subtracted properties
     */
    operator fun Stat.minus(other: Stat): Stat =
        copy(
            base = base - other.base,
            rate = rate - other.rate,
            multiplier = multiplier - other.multiplier,
        )
}

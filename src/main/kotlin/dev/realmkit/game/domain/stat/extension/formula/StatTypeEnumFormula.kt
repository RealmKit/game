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

package dev.realmkit.game.domain.stat.extension.formula

import dev.realmkit.game.domain.aliases.StatTypeFormula

/**
 * # [StatTypeEnumFormula]
 * the stat type enum formula
 */
object StatTypeEnumFormula {
    private const val BY_FIVE = 5.0
    private const val BY_TEN = 10.0
    private const val BY_HUNDRED = 100.0

    /**
     * ## [sumOnly]
     * the `sum` formula (+)
     */
    val sumOnly: StatTypeFormula = { points -> points.toDouble() }

    /**
     * ## [multiplyByFive]
     * the `multiple by five` formula (x10)
     */
    val multiplyByFive: StatTypeFormula = { points -> points * BY_FIVE }

    /**
     * ## [multiplyByTen]
     * the `multiple by ten` formula (x5)
     */
    val multiplyByTen: StatTypeFormula = { points -> points * BY_TEN }

    /**
     * ## [divideByHundred]
     * the `divide by hundred` formula (1%)
     */
    val divideByHundred: StatTypeFormula = { points -> points / BY_HUNDRED }
}

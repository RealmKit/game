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

package dev.realmkit.game.core.extension.operator

import dev.realmkit.game.core.document.CurrentMax
import dev.realmkit.game.core.extension.ConstantExtensions.DOUBLE_ZERO

/**
 * # [CurrentMaxOperator]
 * [dev.realmkit.game.core.document.CurrentMax] operators
 */
object CurrentMaxOperator {
    /**
     * ## [plus]
     * [CurrentMax] `+` operator, sum the `max` only
     *
     * @param other the other [CurrentMax]
     * @return a copy of [CurrentMax] with the summed properties
     */
    operator fun CurrentMax.plus(other: CurrentMax): CurrentMax =
        copy(
            max = max + other.max,
        )

    /**
     * ## [plusAssign]
     * [CurrentMax] `+=` operator
     *
     * @param other the other [CurrentMax]
     */
    operator fun CurrentMax.plusAssign(other: CurrentMax) {
        max += other.max
        current += other.current
        if (current > max) {
            current = max
        }
    }

    /**
     * ## [minus]
     * [CurrentMax] `-` operator, subtract the `max`
     * and if `current` is greater than the new `max`
     * set the `current` to the new `max`
     *
     * @param other the other [CurrentMax]
     * @return a copy of [CurrentMax] with the subtracted properties
     */
    operator fun CurrentMax.minus(other: CurrentMax): CurrentMax =
        copy(
            max = max - other.max,
        ).apply {
            if (max < DOUBLE_ZERO) {
                max = DOUBLE_ZERO
            }
            if (current > max) {
                current = max
            }
        }
}

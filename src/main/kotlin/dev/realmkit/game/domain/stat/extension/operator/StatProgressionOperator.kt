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

import dev.realmkit.game.core.extension.operator.CurrentMaxOperator.plus
import dev.realmkit.game.domain.stat.document.StatProgression

/**
 * # [StatProgressionOperator]
 * [StatProgression] operators
 */
object StatProgressionOperator {
    /**
     * ## [plus]
     * [StatProgression] `+` operator
     *
     * @param other the other [StatProgression]
     * @return a copy of [StatProgression] with the summed properties
     */
    operator fun StatProgression.plus(other: StatProgression): StatProgression =
        copy(
            experience = experience + other.experience,
        )

    /**
     * ## [plusAssign]
     * [StatProgression] `+=` operator
     *
     * @param other the other [StatProgression]
     * @return a copy of [StatProgression] with the summed properties
     */
    operator fun StatProgression.plusAssign(other: StatProgression) {
        experience += other.experience
    }
}

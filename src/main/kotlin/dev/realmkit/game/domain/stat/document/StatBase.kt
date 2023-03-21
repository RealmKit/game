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

package dev.realmkit.game.domain.stat.document

import dev.realmkit.game.core.document.CurrentMax
import dev.realmkit.game.core.extension.ConstantExtensions.DOUBLE_ZERO

/**
 * # [StatBase]
 * the StatBase document
 *
 * @see Stat
 *
 * @property hull `the base` hull points (hp)
 * @property shield `the base` shield points (sp)
 * @property energy `the base` energy points (ep)
 * @property attack `the base` attack attribute (attack)
 * @property defense `the base` defense attribute (defense)
 * @property speed `the base` speed attribute (speed)
 * @property aggro `the base` aggro attribute (aggro)
 */
data class StatBase(
    val hull: CurrentMax = CurrentMax(max = DOUBLE_ZERO, current = DOUBLE_ZERO),
    val shield: CurrentMax = CurrentMax(max = DOUBLE_ZERO, current = DOUBLE_ZERO),
    val energy: CurrentMax = CurrentMax(max = DOUBLE_ZERO, current = DOUBLE_ZERO),
    var attack: Double = DOUBLE_ZERO,
    var defense: Double = DOUBLE_ZERO,
    var speed: Double = DOUBLE_ZERO,
    var aggro: Double = DOUBLE_ZERO,
) {
    companion object
}

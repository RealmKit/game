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

package dev.realmkit.game.domain.staticdata.extension

import kotlin.math.pow
import kotlin.math.roundToLong

/**
 * # [LevelUpFormula]
 * `the level up` formula calculator.
 */
object LevelUpFormula {
    /**
     * ## [POW]
     * consts for creating the level up formula
     */
    private const val POW = 4

    /**
     * ## [MODIFIER]
     * consts for creating the level up formula
     */
    private const val MODIFIER = 0.3

    /**
     * ## [invoke]
     * the required experience to level up
     *
     * @param level the current level
     * @return the required experience to level up
     */
    operator fun invoke(level: Long): Long =
        (level.toDouble().pow(POW) * MODIFIER / (MODIFIER * level)).roundToLong()
}

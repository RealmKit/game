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

/**
 * ## base:
 * attack = 1.0
 * defense = 1.0
 * speed = 1.0
 * hp = max:10.0 actual:10.0
 * mp = max:0.0 actual:0.0
 *
 * ## rate:
 * hpRecoverRate = 0.0
 * mpRecoverRate = 0.0
 *
 * ## multiplier:
 * criticalMultiplier = 1.0
 *
 * ## chance:
 * criticalChance = 0.0
 *
 * ## progression:
 * level = 1
 * xp = 0
 */

package dev.realmkit.game.domain.stat.document

import dev.realmkit.game.domain.base.document.BaseDocument

/**
 * # [Stat]
 * The Stat document.
 *
 * @property progression `the stat` progression
 * @see BaseDocument
 */
data class Stat(
    val progression: StatProgression = StatProgression(),
) {
    companion object
}

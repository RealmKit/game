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

package dev.realmkit.game.envy.domain.stat.document

import dev.realmkit.game.envy.domain.stat.document.value.StatBase
import dev.realmkit.game.envy.domain.stat.document.value.StatChance
import dev.realmkit.game.envy.domain.stat.document.value.StatMultiplier
import dev.realmkit.game.envy.domain.stat.document.value.StatProgression
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Stat(
    val progression: StatProgression = StatProgression(),
    val base: StatBase = StatBase(),
    val multiplier: StatMultiplier = StatMultiplier(),
    val chance: StatChance = StatChance(),
) {
    companion object {
        val zero: Stat
            get() = Stat(
                progression = StatProgression.zero,
                base = StatBase.zero,
                multiplier = StatMultiplier.zero,
                chance = StatChance.zero,
            )
    }
}

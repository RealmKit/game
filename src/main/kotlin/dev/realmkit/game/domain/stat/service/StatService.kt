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

package dev.realmkit.game.domain.stat.service

import dev.realmkit.game.domain.stat.document.Stat
import dev.realmkit.game.domain.staticdata.property.LevelUpFormula
import org.springframework.stereotype.Service

/**
 * # [StatService]
 * the [Stat] service.
 *
 * @see Service
 */
@Service
class StatService {
    /**
     * ## [levelUp]
     * level up the [Stat] if possible
     *
     * @see Stat
     *
     * @param stat the stat to level up
     * @return the leveled stat
     */
    infix fun levelUp(stat: Stat): Stat =
        stat.shouldLevelUp { xpToLevelUp ->
            stat.progression.experience -= xpToLevelUp
            stat.progression.level++
            levelUp(stat)
        }

    /**
     * ## [shouldLevelUp]
     * check if the [Stat] should level up
     *
     * @see Stat
     *
     * @return if the stat should level up or not
     */
    private fun Stat.shouldLevelUp(block: (Long) -> Unit): Stat =
        experienceRequiredToLevelUp().let { xp ->
            if (progression.experience >= xp) {
                block(xp)
            }
            this
        }

    /**
     * ## [experienceRequiredToLevelUp]
     * get the experience required to level up
     *
     * @see Stat
     *
     * @return the experience required to level up
     */
    private fun Stat.experienceRequiredToLevelUp(): Long =
        LevelUpFormula(progression.level + 1)
}

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

import dev.realmkit.game.core.exception.NotEnoughPointsException
import dev.realmkit.game.domain.stat.document.Stat
import dev.realmkit.game.domain.stat.enums.StatTypeEnum
import dev.realmkit.game.domain.staticdata.extension.LevelUpFormula
import dev.realmkit.game.domain.staticdata.property.StaticDataProperties
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

/**
 * # [StatService]
 * the [Stat] service.
 *
 * @see Service
 */
@Service
class StatService(
    private val staticDataProperties: StaticDataProperties,
) {
    /**
     * ## [experienceTable]
     * the `experience to level up` table
     */
    private val experienceTable: ConcurrentHashMap<Long, Long> = ConcurrentHashMap()

    /**
     * ## [experienceRequiredToLevelUp]
     * the required experience to level up
     */
    private val Stat.experienceRequiredToLevelUp: Long
        get() = experienceTable.computeIfAbsent(progression.level + 1) {
            LevelUpFormula(progression.level + 1)
        }

    /**
     * ## [levelUp]
     * level up the [Stat] if possible
     *
     * @see Stat
     *
     * @param stat the stat to level up
     * @return the leveled stat
     */
    infix fun levelUp(stat: Stat): Stat = stat.apply {
        while (progression.experience >= experienceRequiredToLevelUp) {
            progression.experience -= experienceRequiredToLevelUp
            progression.level++
            progression.points += staticDataProperties.config().pointsPerLevel
        }
    }

    /**
     * ## [buy]
     * buy points for a [Stat] attribute
     *
     * @param stat the stat to buy points for
     * @param type the type of the stat to buy points for
     * @param points the number of points to buy
     * @return the stat with the bought points
     * @throws NotEnoughPointsException if the stat does not have enough points to buy
     */
    @Throws(NotEnoughPointsException::class)
    fun buy(
        stat: Stat,
        type: StatTypeEnum,
        points: Long,
    ): Stat = stat.apply {
        if (progression.points < points) {
            throw NotEnoughPointsException(available = progression.points, points = points)
        }

        type.buy(this, points)
        progression.points -= points
    }
}

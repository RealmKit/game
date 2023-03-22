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

package dev.realmkit.game.domain.stat.enums

import dev.realmkit.game.domain.aliases.StatTypeBlock
import dev.realmkit.game.domain.aliases.StatTypeFormula
import dev.realmkit.game.domain.stat.document.Stat
import dev.realmkit.game.domain.stat.extension.formula.StatTypeEnumFormula.divideByHundred
import dev.realmkit.game.domain.stat.extension.formula.StatTypeEnumFormula.multiplyByFive
import dev.realmkit.game.domain.stat.extension.formula.StatTypeEnumFormula.multiplyByTen
import dev.realmkit.game.domain.stat.extension.formula.StatTypeEnumFormula.sumOnly

/**
 * # [StatTypeEnum]
 * the stat type enum
 *
 * @property formula the stat type formula
 */
enum class StatTypeEnum(
    private val formula: StatTypeFormula,
    private val block: StatTypeBlock,
) {
    STAT_BASE_HULL(multiplyByTen, { points -> base.hull.max += points }),
    STAT_BASE_SHIELD(multiplyByFive, { points -> base.shield.max += points }),
    STAT_BASE_ENERGY(multiplyByFive, { points -> base.energy.max += points }),
    STAT_BASE_ATTACK(sumOnly, { points -> base.attack += points }),
    STAT_BASE_DEFENSE(sumOnly, { points -> base.defense += points }),
    STAT_BASE_SPEED(sumOnly, { points -> base.speed += points }),
    STAT_BASE_AGGRO(sumOnly, { points -> base.aggro += points }),
    STAT_RATE_SHIELD_REGENERATION(divideByHundred, { points -> rate.shieldRegeneration += points }),
    STAT_RATE_CRITICAL(divideByHundred, { points -> rate.critical += points }),
    STAT_MULTIPLIER_CRITICAL(divideByHundred, { points -> multiplier.critical += points }),
    ;

    /**
     * ## [buy]
     * buy points based on the type
     *
     * @param stat the stat
     * @param points the points to buy
     * @return the stat itself
     */
    fun buy(stat: Stat, points: Long): Stat =
        stat.apply { block(formula(points)) }
}

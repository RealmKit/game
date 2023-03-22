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
) {
    STAT_BASE_HULL(multiplyByTen),
    STAT_BASE_SHIELD(multiplyByFive),
    STAT_BASE_ENERGY(multiplyByFive),
    STAT_BASE_ATTACK(sumOnly),
    STAT_BASE_DEFENSE(sumOnly),
    STAT_BASE_SPEED(sumOnly),
    STAT_BASE_AGGRO(sumOnly),
    STAT_RATE_SHIELD_REGENERATION(divideByHundred),
    STAT_RATE_CRITICAL(divideByHundred),
    STAT_MULTIPLIER_CRITICAL(divideByHundred),
    ;

    /**
     * @param stat
     * @param points
     */
    fun buy(stat: Stat, points: Long) {
        val calculated = formula(points)
        when (this) {
            STAT_BASE_HULL -> stat.base.hull.max += calculated
            STAT_BASE_SHIELD -> stat.base.shield.max += calculated
            STAT_BASE_ENERGY -> stat.base.energy.max += calculated
            STAT_BASE_ATTACK -> stat.base.attack += calculated
            STAT_BASE_DEFENSE -> stat.base.defense += calculated
            STAT_BASE_SPEED -> stat.base.speed += calculated
            STAT_BASE_AGGRO -> stat.base.aggro += calculated
            STAT_RATE_SHIELD_REGENERATION -> stat.rate.shieldRegeneration += calculated
            STAT_RATE_CRITICAL -> stat.rate.critical += calculated
            STAT_MULTIPLIER_CRITICAL -> stat.multiplier.critical += calculated
        }
    }
}

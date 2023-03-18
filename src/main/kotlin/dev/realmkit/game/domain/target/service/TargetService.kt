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

package dev.realmkit.game.domain.target.service

import dev.realmkit.game.core.extension.ConstantExtensions.DOUBLE_ONE
import dev.realmkit.game.core.extension.ConstantExtensions.DOUBLE_ZERO
import dev.realmkit.game.core.extension.NumberExtensions.isHit
import dev.realmkit.game.domain.battle.action.BattleActionAttack
import dev.realmkit.game.domain.target.document.Target
import org.springframework.stereotype.Service

/**
 * # [TargetService]
 * the [Target] service.
 *
 * @see Service
 */
@Service
class TargetService {
    /**
     * ## [criticalMultiplier]
     * get the critical multiplier if it is critical, else returns 1
     */
    private val Target.criticalMultiplier: Double
        get() = stat.multiplier.critical
            .takeIf { stat.rate.critical.isHit }
            ?: DOUBLE_ONE

    /**
     * ## [hasShield]
     * checks if the target has shield greater than 0.0
     */
    private val Target.hasShield: Boolean
        get() = stat.base.shield.current > DOUBLE_ZERO

    /**
     * ## [attack]
     * attack a [Target], reducing the shield or the hull
     *
     * @param attacker the attacker
     * @param defender the defender
     * @return the attack result
     */
    fun attack(attacker: Target, defender: Target): BattleActionAttack {
        val damage = attacker.stat.base.attack
        val criticalMultiplier = attacker.criticalMultiplier
        val reduction = defender.stat.base.defense

        val finalDamage = (damage * criticalMultiplier) - reduction
        val toTheShield = defender.hasShield

        if (defender.hasShield) {
            defender.stat.base.shield.current -= finalDamage
        } else {
            defender.stat.base.hull.current -= finalDamage
        }

        if (!defender.hasShield) {
            defender.stat.base.shield.current = DOUBLE_ZERO
        }

        return BattleActionAttack(
            attacker = attacker,
            defender = defender,
            finalDamage = finalDamage,
            toTheShield = toTheShield,
            isCritical = criticalMultiplier > DOUBLE_ONE,
        )
    }
}

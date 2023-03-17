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

import dev.realmkit.game.core.extension.ConstantExtensions.ONE
import dev.realmkit.game.core.extension.ConstantExtensions.ZERO
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
     * ## [baseDamage]
     * calculates the base damage from the [Target]
     *
     * @return the base damage
     */
    private val Target.baseDamage: Double
        get() = stat.base.attack

    /**
     * ## [absoluteDamage]
     * calculates the absolute damage from the [Target]
     *
     * @return the critical damage
     */
    private val Target.absoluteDamage: Double
        get() = baseDamage * criticalMultiplier

    /**
     * ## [criticalMultiplier]
     * get the critical multiplier if it is critical, else returns 1
     */
    private val Target.criticalMultiplier: Double
        get() = stat.multiplier.critical
            .takeIf { stat.rate.critical.isHit }
            ?: ONE

    /**
     * ## [absoluteDefense]
     * get the absolute defense of the target
     */
    private val Target.absoluteDefense: Double
        get() = stat.base.defense

    /**
     * ## [hasShield]
     * checks if the target has shield greater than 0.0
     */
    private val Target.hasShield: Boolean
        get() = stat.base.shield.current > ZERO

    /**
     * ## [attack]
     * attack a target, reducing the shield or the hull
     *
     * @param pair the attacker to defender pair
     * @return the attack result
     */
    infix fun attack(pair: Pair<Target, Target>): BattleActionAttack =
        BattleActionAttack(
            attacker = pair.first,
            defender = pair.second,
        ).apply {
            damage = attacker damage defender
            finalDamage = defender reduce damage
            toTheShield = defender.hasShield
            isCritical = damage > attacker.baseDamage

            if (toTheShield) {
                defender.stat.base.shield.current -= damage
            } else {
                defender.stat.base.hull.current -= damage
            }

            if (!defender.hasShield) {
                defender.stat.base.shield.current = ZERO
            }
        }

    /**
     * ## [damage]
     * calculates the [damage] to the target
     *
     * @param target the target to calculate the damage to
     * @return the damage done to the target, or null if none
     */
    private infix fun Target.damage(target: Target): Double =
        absoluteDamage.takeIf { damage -> alive && target.alive && damage > ZERO } ?: ZERO

    /**
     * ## [reduce]
     * reduces the damage to the target
     *
     * @param damage the damage to reduce from
     * @return the damage after reduction, or 0.0 if none
     */
    private infix fun Target.reduce(damage: Double?): Double =
        damage?.minus(absoluteDefense)?.takeIf { reduced -> alive && reduced > ZERO } ?: ZERO
}

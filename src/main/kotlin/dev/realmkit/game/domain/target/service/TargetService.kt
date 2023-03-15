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
     * ## [damage]
     * calculates the final damage
     *
     * @return the critical damage
     */
    private val Target.damage: Double
        get() = stat.base.power *
                criticalMultiplier

    /**
     * ## [criticalMultiplier]
     * get the [criticalMultiplier] if it is critical, else returns 1
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
     * attack a target
     *
     * @param attacker the attacker
     * @param defender the defender
     */
    fun attack(attacker: Target, defender: Target) {
        val damage = attacker damage defender

        if (defender.hasShield) {
            defender.stat.base.shield.current -= damage
            if (!defender.hasShield) {
                defender.stat.base.shield.current = ZERO
            }
        } else {
            defender.stat.base.hull.current -= damage
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
        allAlive(this, target) {
            finalDamage(target)
                .takeIf { damage -> damage > ZERO }
        }

    /**
     * ## [finalDamage]
     * calculates the final damage
     * if `critical`:
     * ```kotlin
     * damage - target.absolutDefense
     * ```
     *
     * @param target the target to calculate the final damage to
     * @return the final damage
     */
    private infix fun Target.finalDamage(target: Target): Double =
        damage - target.absoluteDefense

    /**
     * ## [allAlive]
     * checks if the targets are all alive, if so, executes the block
     *
     * @param targets the targets to check if it is alive
     * @param block the block to execute if the target is alive
     * @return the result of the block, or 0 if the target is not alive
     */
    private fun allAlive(vararg targets: Target, block: () -> Double?): Double =
        takeIf { targets.all { target -> target.alive } }
            ?.let { block() }
            ?: ZERO
}

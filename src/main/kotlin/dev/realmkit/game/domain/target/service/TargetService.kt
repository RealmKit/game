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

import dev.realmkit.game.core.extension.ValidationExtensions.ZERO
import dev.realmkit.game.domain.target.document.Target
import org.springframework.stereotype.Service
import kotlin.random.Random

/**
 * # [TargetService]
 * the [Target] service.
 *
 * @see Service
 */
@Service
class TargetService {
    /**
     * ## [attack]
     * attack a target
     *
     * @see Target
     *
     * @param target `the target` to attack
     */
    infix fun attack(target: Pair<Target, Target>) {
        if (!target.first.alive || !target.second.alive) {
            return
        }
        target.first.damageTo(target.second).also { damage ->
            if (target.second.stat.base.shield.current > ZERO) {
                target.second.stat.base.shield.current -= damage
            } else {
                target.second.stat.base.hull.current -= damage
            }

            if (target.second.stat.base.shield.current < ZERO) {
                target.second.stat.base.shield.current = ZERO
            }
        }
    }

    /**
     * ## [damageTo]
     * calculates the damage to the target
     *
     * @see Target
     *
     * @param target the target to calculate the damage to
     * @return the damage done to the target, or null if none
     */
    private fun Target.damageTo(target: Target): Double =
        if (target.alive) {
            val damage = criticalDamage() - target.stat.base.defense
            if (damage > ZERO) {
                damage
            } else {
                ZERO
            }
        } else {
            ZERO
        }

    /**
     * ## [isCritical]
     * checks if the target is critical
     *
     * @return `true` if the damage is critical, `false` otherwise
     */
    private fun Target.isCritical(): Boolean =
        Random.nextDouble() <= stat.rate.critical

    /**
     * ## [criticalDamage]
     * calculates the critical damage
     *
     * @return the critical damage
     */
    private fun Target.criticalDamage(): Double =
        if (!isCritical()) {
            stat.base.power
        } else {
            stat.base.power * stat.multiplier.critical
        }
}

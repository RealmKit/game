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

package dev.realmkit.game.domain.battle.action

import dev.realmkit.game.core.extension.ConstantExtensions.ZERO
import dev.realmkit.game.domain.target.document.Target

/**
 * # [BattleActionAttack]
 * the battle attack result
 * @property attacker
 * @property defender
 */
class BattleActionAttack(
    val attacker: Target,
    val defender: Target,
) : BattleAction {
    /**
     * ## [damage]
     * the damage amount dealt
     */
    var damage: Double = ZERO

    /**
     * ## [finalDamage]
     * the final damage amount dealt
     */
    var finalDamage: Double = ZERO

    /**
     * ## [toTheShield]
     * flag to indicate if the damage was dealt to the shield
     */
    var toTheShield: Boolean = false

    /**
     * ## [isCritical]
     * flag to indicate if the damage was critical
     */
    var isCritical: Boolean = false
}

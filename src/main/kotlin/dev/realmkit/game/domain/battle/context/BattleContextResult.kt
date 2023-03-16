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

package dev.realmkit.game.domain.battle.context

import dev.realmkit.game.domain.aliases.LogsPerTurn
import dev.realmkit.game.domain.battle.action.BattleActionAttack
import dev.realmkit.game.domain.battle.action.BattleActionAttackerAttempt
import dev.realmkit.game.domain.battle.action.BattleActionAttackerRepeatAttempt
import dev.realmkit.game.domain.target.document.Target

/**
 * # [BattleContextResult]
 * the `battle context result` for keeping the battle logs
 */
class BattleContextResult {
    /**
     * ## [logsPerTurn]
     * the `logs per turn` map
     */
    val logsPerTurn: LogsPerTurn = mutableMapOf()

    /**
     * ## [turns]
     * the `turns` counter
     */
    var turns: Long = 0

    /**
     * ## [registerTurn]
     * register a new turn
     */
    fun registerTurn() {
        turns++
        logsPerTurn[turns] = linkedSetOf()
    }

    /**
     * ## [registerAttackResults]
     * register the attack results
     *
     * @param result the `result` to register
     */
    infix fun registerAttackResults(result: BattleActionAttack) {
        logsPerTurn[turns]?.add(result)
    }

    /**
     * ## [registerAttackerAttempt]
     * register the attacker attempt
     *
     * @param target the `target` to register
     */
    infix fun registerAttackerAttempt(target: Target) {
        val speed = target.stat.base.speed
        logsPerTurn[turns]?.add(
            BattleActionAttackerAttempt(
                attacker = target.id,
                speed = speed,
            ),
        )
    }

    /**
     * ## [registerAttackerRepeatAttempt]
     * register the attacker repeat attempt
     *
     * @param target the `target` to register
     */
    infix fun registerAttackerRepeatAttempt(target: Target) {
        val hull = target.stat.base.hull.current
        val shield = target.stat.base.shield.current
        logsPerTurn[turns]?.add(
            BattleActionAttackerRepeatAttempt(
                attacker = target.id,
                hull = hull,
                shield = shield,
                alive = target.alive,
            ),
        )
    }
}

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
import dev.realmkit.game.domain.battle.action.BattleActionFinalResult
import dev.realmkit.game.domain.battle.enums.BattleActionFinalResultType
import dev.realmkit.game.domain.battle.enums.BattleActionFinalResultType.ATTACKERS_WIN
import dev.realmkit.game.domain.battle.enums.BattleActionFinalResultType.DEFENDERS_WIN
import dev.realmkit.game.domain.battle.enums.BattleActionFinalResultType.DRAW
import dev.realmkit.game.domain.target.document.Target
import dev.realmkit.game.domain.target.extension.TargetExtensions.hasAlive

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
     * ## [finalResult]
     * the `final result` of the battle
     */
    val finalResult: BattleActionFinalResult
        get() = logsPerTurn[turns]!!.last() as BattleActionFinalResult

    /**
     * ## [registerTurn]
     * register a new turn
     *
     * @return itself
     */
    fun registerTurn(): BattleContextResult = apply {
        turns++
        logsPerTurn[turns] = linkedSetOf()
    }

    /**
     * ## [registerAttackResults]
     * register the attack results
     *
     * @param result the `result` to register
     * @return itself
     */
    infix fun registerAttackResults(result: BattleActionAttack): BattleContextResult = apply {
        logsPerTurn[turns]!!.add(result)
    }

    /**
     * ## [registerAttackerAttempt]
     * register the attacker attempt
     *
     * @param target the `target` to register
     * @return itself
     */
    infix fun registerAttackerAttempt(target: Target): BattleContextResult = apply {
        val speed = target.stat.base.speed
        logsPerTurn[turns]!!.add(
            BattleActionAttackerAttempt(
                attacker = target.id,
                speed = speed,
            ),
        )
    }

    /**
     * ## [registerFinalResult]
     * register the battle result, who won and who lost or if it was a draw
     *
     * @see BattleContextResult
     *
     * @param attackers the `attackers` to register
     * @param defenders the `defenders` to register
     * @return itself
     */
    fun registerFinalResult(attackers: MutableSet<Target>, defenders: MutableSet<Target>): BattleContextResult = apply {
        logsPerTurn[turns]!!.add(
            BattleActionFinalResult(
                result = resultType(attackers, defenders),
                attackers = attackers,
                defenders = defenders,
            ),
        )
    }

    /**
     * ## [resultType]
     * get the battle final result type
     *
     * @see BattleActionFinalResultType
     *
     * @param attackers the `attackers` to check
     * @param defenders the `defenders` to check
     * @return the `battle final result type` enum
     */
    private fun resultType(attackers: MutableSet<Target>, defenders: MutableSet<Target>): BattleActionFinalResultType =
        when {
            attackers.hasAlive && !defenders.hasAlive -> ATTACKERS_WIN
            !attackers.hasAlive && defenders.hasAlive -> DEFENDERS_WIN
            else -> DRAW
        }
}

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
import dev.realmkit.game.domain.target.document.Target

/**
 * # [BattleContextResult]
 * the `battle context result` for keeping the battle logs
 */
class BattleContextResult {
    /**
     * ## [attackers]
     * the `attackers` set of the battle
     */
    val attackers: MutableSet<Target> = mutableSetOf()

    /**
     * ## [defenders]
     * the `defenders` set of the battle
     */
    val defenders: MutableSet<Target> = mutableSetOf()

    /**
     * ## [logsPerTurn]
     * the `logs per turn` map
     */
    val logsPerTurn: LogsPerTurn = mutableMapOf()

    /**
     * ## [turns]
     * the `turns` counter
     */
    var turns: Long = 0L

    /**
     * ## [start]
     * start the battle
     *
     * @param attackers the `attackers` set
     * @param defenders the `defenders` set
     */
    fun start(attackers: Set<Target>, defenders: Set<Target>) {
        this.attackers.addAll(attackers)
        this.defenders.addAll(defenders)
    }

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
}

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

import dev.realmkit.game.core.extension.NumberExtensions.repeat
import dev.realmkit.game.domain.aliases.AttackBlock
import dev.realmkit.game.domain.aliases.AttackerTargets
import dev.realmkit.game.domain.staticdata.document.StaticDataBattle
import dev.realmkit.game.domain.target.document.Target
import dev.realmkit.game.domain.target.extension.TargetExtensions.bySpeed
import dev.realmkit.game.domain.target.extension.TargetExtensions.firstByAggro
import dev.realmkit.game.domain.target.extension.TargetExtensions.hasAlive

/**
 * # [BattleContext]
 * the battle context
 *
 * @see Target
 *
 * @property properties the battle properties
 * @property onAttack the attack block
 */
class BattleContext(
    private val properties: StaticDataBattle,
    private val onAttack: AttackBlock,
) {
    private val battleContextResult: BattleContextResult = BattleContextResult()
    private val attackers: MutableSet<Target> = mutableSetOf()
    private val defenders: MutableSet<Target> = mutableSetOf()

    /**
     * ## [battleIsNotOver]
     * check if the battle is over or not
     */
    private val battleIsNotOver: Boolean
        get() = battleContextResult.turns < properties.battleDuration &&
                attackers.hasAlive &&
                defenders.hasAlive

    /**
     * ## [attack]
     * attack the targets, based on their aggro
     *
     * @see Target
     *
     * @param targets the targets to attack
     * @return target, if exists
     */
    private infix fun Target.attack(targets: Iterable<Target>): Target? =
        targets.firstByAggro { target ->
            battleContextResult registerAttackResults onAttack(this, target)
        }

    /**
     * ## [against]
     * add the targets to the battle
     *
     * @see Target
     *
     * @param second the target to add
     * @return nothing
     */
    infix fun Target.against(second: Target) =
        setOf(this).against(setOf(second))

    /**
     * ## [against]
     * add the targets to the battle
     *
     * @see Target
     *
     * @param second the targets to add
     * @return nothing
     */
    infix fun Target.against(second: Iterable<Target>) =
        setOf(this).against(second)

    /**
     * ## [against]
     * add the targets to the battle
     *
     * @see Target
     *
     * @param second the target to add
     * @return nothing
     */
    infix fun Iterable<Target>.against(second: Target) =
        this.against(setOf(second))

    /**
     * ## [against]
     * add the targets to the battle
     *
     * @see Target
     *
     * @param second the targets to add
     * @return nothing
     */
    infix fun Iterable<Target>.against(second: Iterable<Target>) {
        attackers.addAll(this)
        defenders.addAll(second)
    }

    /**
     * ## [start]
     * start the battle until the battle is over
     *
     * @return the battle result
     */
    fun start(): BattleContextResult {
        battleContextResult.start(attackers = attackers, defenders = defenders)
        while (battleIsNotOver) {
            battleContextResult.registerTurn()
            attackers.versus(defenders).bySpeed.forEach { (target, targets) ->
                target.takeIf { attacker -> attacker.alive }
                    ?.let { attacker ->
                        attacker.stat.base.speed.repeat {
                            attacker attack targets
                        }
                    }
            }
        }
        return battleContextResult
    }

    /**
     * ## [versus]
     * create the attacker targets
     *
     * @see AttackerTargets
     *
     * @param targets the targets to attack
     * @return the attacker+defenders targets
     */
    private infix fun Set<Target>.versus(targets: Set<Target>): Iterable<AttackerTargets> =
        this.map { target -> target to targets } + targets.map { target -> target to this }

    companion object
}

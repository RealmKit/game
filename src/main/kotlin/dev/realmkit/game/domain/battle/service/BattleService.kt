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

package dev.realmkit.game.domain.battle.service

import dev.realmkit.game.core.extension.ConstantExtensions.LONG_ZERO
import dev.realmkit.game.domain.battle.action.BattleActionAttack
import dev.realmkit.game.domain.battle.context.BattleContext
import dev.realmkit.game.domain.battle.context.BattleContextResult
import dev.realmkit.game.domain.player.document.Player
import dev.realmkit.game.domain.player.service.PlayerService
import dev.realmkit.game.domain.staticdata.property.StaticDataProperties
import dev.realmkit.game.domain.target.document.Target
import dev.realmkit.game.domain.target.service.TargetService
import org.springframework.stereotype.Service

/**
 * # [BattleService]
 * the Battle service for handling the Battle system
 */
@Service
class BattleService(
    private val playerService: PlayerService,
    private val targetService: TargetService,
    private val staticDataProperties: StaticDataProperties,
) {
    /**
     * ## [totalExperience]
     * calculates the total experience of the [Target] that were defeated
     *
     * @return the total experience
     */
    private val <T : Target> Set<T>.totalExperience: Long
        get() = filter { target -> !target.alive }
            .sumOf { target -> target.ship.stat.progression.experience }

    /**
     * ## [battle]
     * starts a battle within the [BattleContext] block
     *
     * @see BattleContext
     *
     * @param block the battle context
     * @return the battle result
     */
    fun battle(block: BattleContext.() -> Unit): BattleContextResult =
        BattleContext(
            battleDuration = staticDataProperties.battleDuration(),
            onAttack = ::onAttack,
        ).apply(block)
            .start()
            .applyResults()

    /**
     * ## [onAttack]
     * the attack block
     *
     * @param attacker the first target
     * @param defender the second target
     * @return the attack result
     */
    private fun onAttack(attacker: Target, defender: Target): BattleActionAttack =
        targetService.attack(attacker, defender)

    /**
     * ## [applyResults]
     * applies the battle results to the [dev.realmkit.game.domain.player.document.Player]
     *
     * @return itself
     */
    private fun BattleContextResult.applyResults(): BattleContextResult = apply {
        updateExperience(attackers, defenders.totalExperience)
        updateExperience(defenders, attackers.totalExperience)
    }

    /**
     * ## [updateExperience]
     * updates the experience of the [Target]
     *
     * @param targets the targets to update
     * @param experienceToAdd the experience to add
     * @return nothing
     */
    private fun updateExperience(targets: Set<Target>, experienceToAdd: Long) {
        if (experienceToAdd == LONG_ZERO) {
            return
        }
        targets.filterIsInstance<Player>()
            .onEach { player ->
                player.ship.stat.progression.experience += experienceToAdd
                playerService.update(player)
            }
    }
}

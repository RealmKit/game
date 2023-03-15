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

import dev.realmkit.game.domain.battle.context.BattleContext
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
    private val targetService: TargetService,
    private val staticDataProperties: StaticDataProperties,
) {
    /**
     * ## [onAttack]
     * the attack block
     *
     * @param attacker the first target
     * @param defender the second target
     * @return nothing
     */
    private fun onAttack(attacker: Target, defender: Target): Double =
        targetService attack (attacker to defender)

    /**
     * ## [battle]
     * starts a battle within the [BattleContext] block
     *
     * @see BattleContext
     *
     * @param block the battle context
     * @return the battle context
     */
    fun battle(block: BattleContext.() -> Unit): BattleContext =
        BattleContext(
            properties = staticDataProperties.battle(),
            onAttack = ::onAttack,
        ).apply(block).start()
}

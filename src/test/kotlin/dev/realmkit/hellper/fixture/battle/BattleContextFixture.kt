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

package dev.realmkit.hellper.fixture.battle

import dev.realmkit.game.domain.battle.action.BattleActionAttack
import dev.realmkit.game.domain.battle.context.BattleContext
import dev.realmkit.game.domain.player.document.Player
import dev.realmkit.game.domain.staticdata.document.StaticDataBattle
import dev.realmkit.hellper.extension.RandomSourceExtensions.positiveLong
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary

/**
 * Creates a [Player] with random data
 */
val BattleContext.Companion.fixture: Arb<BattleContext>
    get() = arbitrary { rs ->
        BattleContext(
            properties = StaticDataBattle(
                battleDuration = rs.positiveLong(),
                turnDuration = rs.positiveLong(),
            ),
            onAttack = { attacker, defender ->
                BattleActionAttack(
                    attacker = attacker,
                    defender = defender,
                ).apply {
                    finalDamage = attacker.stat.base.attack
                    toTheShield = false
                    isCritical = false
                    defender.stat.base.hull.current -= finalDamage
                }
            },
        )
    }

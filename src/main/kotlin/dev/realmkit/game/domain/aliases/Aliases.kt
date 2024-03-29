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

package dev.realmkit.game.domain.aliases

import dev.realmkit.game.domain.battle.action.BattleAction
import dev.realmkit.game.domain.battle.action.BattleActionAttack
import dev.realmkit.game.domain.stat.document.Stat
import dev.realmkit.game.domain.target.document.Target

/**
 * # [AttackBlock]
 * the `attack block` alias
 */
typealias AttackBlock = (Target, Target) -> BattleActionAttack

/**
 * # [AttackerTargets]
 * the `attacker to targets` alias
 */
typealias AttackerTargets = Pair<Target, Set<Target>>

/**
 * # [LogsPerTurn]
 * the `logs per turn` alias
 */
typealias LogsPerTurn = MutableMap<Long, LinkedHashSet<BattleAction>>

/**
 * # [StatTypeFormula]
 * the `stat type formula` alias
 */
typealias StatTypeFormula = (Long) -> Double

/**
 * # [StatTypeBlock]
 * the `stat type block` alias
 */
typealias StatTypeBlock = Stat.(Double) -> Unit

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

package dev.realmkit.game.domain.target.extension

import dev.realmkit.game.domain.aliases.AttackerTargets
import dev.realmkit.game.domain.target.document.Target

/**
 * # [TargetExtensions]
 * extension functions for [Target]
 *
 * @see Target
 */
object TargetExtensions {
    /**
     * ## [sortBySpeed]
     * sort the targets by [dev.realmkit.game.domain.stat.document.StatBase.speed]
     */
    private val sortBySpeed: Comparator<AttackerTargets> = compareBy { target -> -target.first.stat.base.speed }

    /**
     * ## [sortByAggro]
     * sort the targets by [dev.realmkit.game.domain.stat.document.StatBase.aggro]
     */
    private val sortByAggro: Comparator<Target> = compareBy { target: Target -> -target.stat.base.aggro }

    /**
     * ## [hasAlive]
     * check if there is any [Target] still [Target.alive]
     */
    val Set<Target>.hasAlive: Boolean
        get() = any { target -> target.alive }

    /**
     * ## [bySpeed]
     * filter [Target.alive] and sort the targets by [dev.realmkit.game.domain.stat.document.StatBase.speed]
     */
    val Iterable<AttackerTargets>.bySpeed: List<AttackerTargets>
        get() = filter { target -> target.first.alive }
            .sortedWith(sortBySpeed)

    /**
     * ## [byAggro]
     * filter [Target.alive] and sort the targets by [dev.realmkit.game.domain.stat.document.StatBase.aggro]
     */
    private val Iterable<Target>.byAggro: List<Target>
        get() = filter { target -> target.alive }
            .sortedWith(sortByAggro)

    /**
     * ## [firstByAggro]
     * get first [Target] by [dev.realmkit.game.domain.stat.document.StatBase.aggro], if exists
     *
     * @param block the block to execute if the [Target] exists
     * @return the first [Target] by [dev.realmkit.game.domain.stat.document.StatBase.aggro], if exists
     */
    infix fun Set<Target>.firstByAggro(block: (Target) -> Unit): Target? =
        byAggro.firstOrNull()
            ?.apply(block)
}

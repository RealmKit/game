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

package dev.realmkit.game.envy.domain.stat.property

import dev.realmkit.game.envy.data.properties.StaticProperties.ZeroStaticProperties.BASE_STATIC_LEVEL
import dev.realmkit.game.envy.data.properties.StaticProperties.ZeroStaticProperties.BASE_STATIC_ZERO_MULTIPLIER
import dev.realmkit.game.envy.data.properties.StaticProperties.ZeroStaticProperties.BASE_STATIC_ZERO_VALUE
import dev.realmkit.game.envy.domain.stat.document.Stat
import dev.realmkit.game.envy.domain.stat.document.value.StatBase
import dev.realmkit.game.envy.domain.stat.document.value.StatChance
import dev.realmkit.game.envy.domain.stat.document.value.StatMultiplier
import dev.realmkit.game.envy.domain.stat.document.value.StatProgression
import dev.realmkit.game.envy.domain.stat.document.value.StatValue

/**
 * [StatsProperties]
 * Defines all stats belonging to an item, player, skill, or whatever could have stats
 */
sealed class StatsProperties {
    /**
     * the level attribute
     */
    open val level: Long = BASE_STATIC_LEVEL

    /**
     * the experience attribute
     */
    open val experience: Long = BASE_STATIC_ZERO_VALUE

    /**
     * the health attribute
     */
    open val health: Long = BASE_STATIC_ZERO_VALUE

    /**
     * the mana attribute
     */
    open val mana: Long = BASE_STATIC_ZERO_VALUE

    /**
     * the stamina attribute
     */
    open val stamina: Long = BASE_STATIC_ZERO_VALUE

    /**
     * the attack attribute
     */
    open val attack: Long = BASE_STATIC_ZERO_VALUE

    /**
     * the attack attribute
     */
    open val magic: Long = BASE_STATIC_ZERO_VALUE

    /**
     * the speed attribute
     */
    open val speed: Long = BASE_STATIC_ZERO_VALUE

    /**
     * the defense attribute
     */
    open val defense: Long = BASE_STATIC_ZERO_VALUE

    /**
     * the experienceMultiplier attribute
     */
    open val experienceMultiplier: Double = BASE_STATIC_ZERO_MULTIPLIER

    /**
     * the dropMultiplier attribute
     */
    open val dropMultiplier: Double = BASE_STATIC_ZERO_MULTIPLIER

    /**
     * the criticalMultiplier attribute
     */
    open val criticalMultiplier: Double = BASE_STATIC_ZERO_MULTIPLIER

    /**
     * the criticalChance attribute
     */
    open val criticalChance: Double = BASE_STATIC_ZERO_MULTIPLIER

    /**
     * the evadeChance attribute
     */
    open val evadeChance: Double = BASE_STATIC_ZERO_MULTIPLIER

    /**
     * Generate a [Stat] from the properties
     */
    val stat: Stat
        get() = Stat(
            progression = StatProgression(
                level = level,
                experience = experience,
            ),
            base = StatBase(
                health = StatValue(max = health),
                mana = StatValue(max = mana),
                stamina = StatValue(max = stamina),
                attack = attack,
                magic = magic,
                speed = speed,
                defense = defense,
            ),
            multiplier = StatMultiplier(
                experience = experienceMultiplier,
                drop = dropMultiplier,
                critical = criticalMultiplier,
            ),
            chance = StatChance(
                critical = criticalChance,
                evade = evadeChance,
            ),
        )

    companion object
}

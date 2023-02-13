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

import dev.realmkit.game.envy.data.property.StaticProperty.NewbieStaticProperties.BASE_NEWBIE_ATTACK
import dev.realmkit.game.envy.data.property.StaticProperty.NewbieStaticProperties.BASE_NEWBIE_DEFENSE
import dev.realmkit.game.envy.data.property.StaticProperty.NewbieStaticProperties.BASE_NEWBIE_HEALTH
import dev.realmkit.game.envy.data.property.StaticProperty.NewbieStaticProperties.BASE_NEWBIE_SPEED
import dev.realmkit.game.envy.data.property.StaticProperty.NewbieStaticProperties.BASE_NEWBIE_STAMINA
import dev.realmkit.game.envy.domain.stat.property.NewbieStatProperties.attack
import dev.realmkit.game.envy.domain.stat.property.NewbieStatProperties.criticalChance
import dev.realmkit.game.envy.domain.stat.property.NewbieStatProperties.criticalMultiplier
import dev.realmkit.game.envy.domain.stat.property.NewbieStatProperties.dropMultiplier
import dev.realmkit.game.envy.domain.stat.property.NewbieStatProperties.evadeChance
import dev.realmkit.game.envy.domain.stat.property.NewbieStatProperties.experience
import dev.realmkit.game.envy.domain.stat.property.NewbieStatProperties.experienceMultiplier
import dev.realmkit.game.envy.domain.stat.property.NewbieStatProperties.health
import dev.realmkit.game.envy.domain.stat.property.NewbieStatProperties.level
import dev.realmkit.game.envy.domain.stat.property.NewbieStatProperties.mana
import dev.realmkit.game.envy.domain.stat.property.NewbieStatProperties.speed
import dev.realmkit.game.envy.domain.stat.property.NewbieStatProperties.stamina

/**
 * [NewbieStatProperties]
 * Defines all stats belonging to an item, player, skill, or whatever could have stats
 *
 * @property level the `thing` level
 * @property experience the `thing` experience to level
 * @property health the `thing` health modifier attribute
 * @property mana the `thing` mana modifier attribute
 * @property stamina the `thing` stamina modifier attribute
 * @property attack the `thing` attack modifier attribute
 * @property speed the `thing` speed modifier attribute
 * @property experienceMultiplier the `thing` experience modifier multiplier
 * @property dropMultiplier the `thing` drop rate modifier multiplier
 * @property criticalMultiplier the `thing` critical modifier multiplier
 * @property criticalChance the `thing` critical chance modifier multiplier
 * @property evadeChance the `thing` evade chance modifier multiplier
 */
object NewbieStatProperties : StatProperties() {
    override val health: Long = BASE_NEWBIE_HEALTH
    override val stamina: Long = BASE_NEWBIE_STAMINA
    override val attack: Long = BASE_NEWBIE_ATTACK
    override val speed: Long = BASE_NEWBIE_SPEED
    override val defense: Long = BASE_NEWBIE_DEFENSE
}

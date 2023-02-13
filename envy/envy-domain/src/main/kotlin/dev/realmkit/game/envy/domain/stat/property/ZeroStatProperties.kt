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

import dev.realmkit.game.envy.domain.stat.property.ZeroStatProperties.attack
import dev.realmkit.game.envy.domain.stat.property.ZeroStatProperties.criticalChance
import dev.realmkit.game.envy.domain.stat.property.ZeroStatProperties.criticalMultiplier
import dev.realmkit.game.envy.domain.stat.property.ZeroStatProperties.dropMultiplier
import dev.realmkit.game.envy.domain.stat.property.ZeroStatProperties.evadeChance
import dev.realmkit.game.envy.domain.stat.property.ZeroStatProperties.experience
import dev.realmkit.game.envy.domain.stat.property.ZeroStatProperties.experienceMultiplier
import dev.realmkit.game.envy.domain.stat.property.ZeroStatProperties.health
import dev.realmkit.game.envy.domain.stat.property.ZeroStatProperties.level
import dev.realmkit.game.envy.domain.stat.property.ZeroStatProperties.mana
import dev.realmkit.game.envy.domain.stat.property.ZeroStatProperties.speed
import dev.realmkit.game.envy.domain.stat.property.ZeroStatProperties.stamina

/**
 * [ZeroStatProperties]
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
object ZeroStatProperties : StatProperties()
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

package dev.realmkit.test.sloth.testutils.extensions

import dev.realmkit.game.envy.domain.stat.document.Stat
import dev.realmkit.game.envy.domain.stat.document.value.StatBase
import dev.realmkit.game.envy.domain.stat.document.value.StatChance
import dev.realmkit.game.envy.domain.stat.document.value.StatMultiplier
import dev.realmkit.game.envy.domain.stat.document.value.StatProgression
import dev.realmkit.game.envy.domain.stat.document.value.StatValue
import io.kotest.matchers.longs.shouldBeGreaterThanOrEqual
import io.kotest.matchers.nulls.shouldNotBeNull

/**
 * Basic validation for a [Stat]
 *
 * @return the [Stat] itself
 * @see [Stat]
 */
fun Stat?.shouldBeValid(): Stat {
    shouldNotBeNull()
    base.shouldBeValid()
    multiplier.shouldBeValid()
    chance.shouldBeValid()
    progression.shouldBeValid()
    return this
}

/**
 * Basic validation for a [StatBase]
 *
 * @return the [StatBase] itself
 * @see [StatBase]
 */
fun StatBase?.shouldBeValid(): StatBase {
    shouldNotBeNull()
    health.shouldBeValid()
    mana.shouldBeValid()
    stamina.shouldBeValid()
    attack.shouldBeGreaterThanOrEqualZero()
    magic.shouldBeGreaterThanOrEqualZero()
    speed.shouldBeGreaterThanOrEqualZero()
    defense.shouldBeGreaterThanOrEqualZero()
    return this
}

/**
 * Basic validation for a [StatMultiplier]
 *
 * @return the [StatMultiplier] itself
 * @see [StatMultiplier]
 */
fun StatMultiplier.shouldBeValid(): StatMultiplier {
    shouldNotBeNull()
    experience.shouldBeGreaterThanOrEqualZero()
    drop.shouldBeGreaterThanOrEqualZero()
    critical.shouldBeGreaterThanOrEqualZero()
    return this
}

/**
 * Basic validation for a [StatChance]
 *
 * @return the [StatChance] itself
 * @see [StatChance]
 */
fun StatChance.shouldBeValid(): StatChance {
    shouldNotBeNull()
    critical.shouldBeGreaterThanOrEqualZero()
    evade.shouldBeGreaterThanOrEqualZero()
    return this
}

/**
 * Basic validation for a [StatProgression]
 *
 * @return the [StatProgression] itself
 * @see [StatProgression]
 */
fun StatProgression.shouldBeValid(): StatProgression {
    shouldNotBeNull()
    level.shouldBeGreaterThanOrEqualZero()
    experience.shouldBeGreaterThanOrEqualZero()
    return this
}

/**
 * Basic validation for a [StatValue]
 *
 * @return the [StatValue] itself
 * @see [StatValue]
 */
fun StatValue?.shouldBeValid(): StatValue {
    shouldNotBeNull()
    current.shouldBeGreaterThanOrEqualZero()
    max shouldBeGreaterThanOrEqual current
    return this
}

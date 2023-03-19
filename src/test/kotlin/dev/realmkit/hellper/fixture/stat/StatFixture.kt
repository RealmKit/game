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

package dev.realmkit.hellper.fixture.stat

import dev.realmkit.game.core.extension.ConstantExtensions
import dev.realmkit.game.domain.stat.document.Stat
import dev.realmkit.game.domain.stat.document.StatBase
import dev.realmkit.game.domain.stat.document.StatMultiplier
import dev.realmkit.game.domain.stat.document.StatProgression
import dev.realmkit.game.domain.stat.document.StatRate
import dev.realmkit.hellper.fixture.Fixture
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary

/**
 * Creates a [Stat] with random data
 */
val Stat.Companion.fixture: Arb<Stat>
    get() = arbitrary {
        val base = StatBase.fixture.bind()
        val rate = StatRate.fixture.bind()
        val multiplier = StatMultiplier.fixture.bind()
        val progression = StatProgression.fixture.bind()
        Fixture {
            Stat::base { base }
            Stat::rate { rate }
            Stat::multiplier { multiplier }
            Stat::progression { progression }
        }
    }

/**
 * Creates a [Stat] with random invalid data
 */
val Stat.Companion.invalid: Arb<Stat>
    get() = arbitrary {
        val base = StatBase.invalid.bind()
        val rate = StatRate.invalid.bind()
        val multiplier = StatMultiplier.invalid.bind()
        val progression = StatProgression.invalid.bind()
        Fixture {
            Stat::base { base }
            Stat::rate { rate }
            Stat::multiplier { multiplier }
            Stat::progression { progression }
        }
    }

/**
 * ## [prepareToWinBattle]
 * sets the stats to be very high
 */
fun Stat.prepareToWinBattle() {
    base.hull.max = Double.MAX_VALUE
    base.hull.current = Double.MAX_VALUE
    base.shield.max = Double.MAX_VALUE
    base.shield.current = Double.MAX_VALUE
    base.attack = Double.MAX_VALUE
    base.defense = Double.MAX_VALUE
    base.speed = ConstantExtensions.DOUBLE_ONE
    multiplier.critical = ConstantExtensions.DOUBLE_ONE
    progression.level = ConstantExtensions.LONG_ONE
    progression.experience = ConstantExtensions.LONG_ZERO
}

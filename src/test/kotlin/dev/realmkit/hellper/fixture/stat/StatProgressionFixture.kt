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

import dev.realmkit.game.domain.stat.document.StatProgression
import dev.realmkit.hellper.extension.FakerExtensions.positiveLong
import io.kotest.property.Arb
import io.kotest.property.Shrinker
import io.kotest.property.arbitrary.arbitrary

/**
 * Creates a [StatProgression] [Arb] with random bind data
 */
val StatProgression.Companion.arbitrary: Arb<StatProgression>
    get() = arbitrary(shrinker) {
        fixture(
            level = positiveLong.bind(),
            experience = positiveLong.bind(),
        )
    }

/**
 * Creates a [StatProgression] [Shrinker] withing the old StatProgression
 */
val StatProgression.Companion.shrinker: Shrinker<StatProgression>
    get() = Shrinker { progression ->
        listOf(
            progression.copy(level = progression.level - 1),
            progression.copy(level = progression.level + 1),
            progression.copy(experience = progression.experience - 1),
            progression.copy(experience = progression.experience + 1),
            progression.copy(level = progression.level - 1, experience = progression.experience - 1),
            progression.copy(level = progression.level + 1, experience = progression.experience + 1),
        )
    }

/**
 * Creates a [StatProgression] with random data
 *
 * @param level the progression level
 * @param experience the progression experience
 * @return the StatProgression
 */
fun StatProgression.Companion.fixture(
    level: Long = 1,
    experience: Long = 0,
): StatProgression = StatProgression(
    level = level,
    experience = experience,
)

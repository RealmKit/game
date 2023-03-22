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

package dev.realmkit.game.domain.stat.extension.operator

import dev.realmkit.game.domain.stat.document.Stat
import dev.realmkit.game.domain.stat.extension.operator.StatOperator.minus
import dev.realmkit.game.domain.stat.extension.operator.StatOperator.plus
import dev.realmkit.hellper.extension.AssertionExtensions.shouldBeSubtractedOf
import dev.realmkit.hellper.extension.AssertionExtensions.shouldBeSumOf
import dev.realmkit.hellper.fixture.stat.StatFixture.fixture
import dev.realmkit.hellper.spec.TestSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.checkAll

class StatOperatorTest : TestSpec({
    expect("to SUM two Stat") {
        checkAll(Stat.fixture, Stat.fixture) { actual, other ->
            val sum = actual + other
            sum.shouldBeSumOf(actual, other)
        }
    }

    expect("to SUBTRACT two Stat") {
        checkAll(Stat.fixture, Stat.fixture) { actual, other ->
            val subtract = actual - other
            subtract.shouldBeSubtractedOf(actual, other)
        }
    }

    expect("to SUBTRACT two Stat, with max > current") {
        checkAll(Stat.fixture, Stat.fixture) { actual, other ->
            actual.base.hull.max = 10.0
            actual.base.hull.current = 1.0
            actual.base.shield.max = 10.0
            actual.base.shield.current = 1.0
            actual.base.energy.max = 10.0
            actual.base.energy.current = 1.0
            other.base.hull.max = 5.0
            other.base.shield.max = 5.0
            other.base.energy.max = 5.0

            val subtract = actual - other
            subtract.shouldBeSubtractedOf(actual, other)
            subtract.base.hull.max shouldBe 5.0
            subtract.base.hull.current shouldBe 1.0
            subtract.base.shield.max shouldBe 5.0
            subtract.base.shield.current shouldBe 1.0
            subtract.base.energy.max shouldBe 5.0
            subtract.base.energy.current shouldBe 1.0
            subtract.progression.level shouldBe actual.progression.level
            subtract.progression.experience shouldBe actual.progression.experience
            subtract.progression.points shouldBe actual.progression.points
        }
    }

    expect("to SUBTRACT two Stat, with max < current") {
        checkAll(Stat.fixture, Stat.fixture) { actual, other ->
            actual.base.hull.max = 10.0
            actual.base.hull.current = 10.0
            actual.base.shield.max = 10.0
            actual.base.shield.current = 10.0
            actual.base.energy.max = 10.0
            actual.base.energy.current = 10.0
            other.base.hull.max = 5.0
            other.base.shield.max = 5.0
            other.base.energy.max = 5.0

            val subtract = actual - other
            subtract.shouldBeSubtractedOf(actual, other)
            subtract.base.hull.max shouldBe 5.0
            subtract.base.hull.current shouldBe 5.0
            subtract.base.shield.max shouldBe 5.0
            subtract.base.shield.current shouldBe 5.0
            subtract.base.energy.max shouldBe 5.0
            subtract.base.energy.current shouldBe 5.0
            subtract.progression.level shouldBe actual.progression.level
            subtract.progression.experience shouldBe actual.progression.experience
            subtract.progression.points shouldBe actual.progression.points
        }
    }
})

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

import dev.realmkit.game.domain.stat.document.StatBase
import dev.realmkit.game.domain.stat.extension.operator.StatBaseOperator.minus
import dev.realmkit.game.domain.stat.extension.operator.StatBaseOperator.plus
import dev.realmkit.hellper.extension.AssertionExtensions.shouldBeSubtractedOf
import dev.realmkit.hellper.extension.AssertionExtensions.shouldBeSumOf
import dev.realmkit.hellper.fixture.stat.StatBaseFixture.fixture
import dev.realmkit.hellper.spec.TestSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.checkAll

class StatBaseOperatorTest : TestSpec({
    expect("to SUM two StatBase") {
        checkAll(StatBase.fixture, StatBase.fixture) { actual, other ->
            val sum = actual + other
            sum.shouldBeSumOf(actual, other)
        }
    }

    expect("to SUBTRACT two StatBase") {
        checkAll(StatBase.fixture, StatBase.fixture) { actual, other ->
            val subtract = actual - other
            subtract.shouldBeSubtractedOf(actual, other)
        }
    }

    expect("to SUBTRACT two StatBase, with max > current") {
        checkAll(StatBase.fixture, StatBase.fixture) { actual, other ->
            actual.hull.max = 10.0
            actual.hull.current = 1.0
            actual.shield.max = 10.0
            actual.shield.current = 1.0
            actual.energy.max = 10.0
            actual.energy.current = 1.0
            other.hull.max = 5.0
            other.shield.max = 5.0
            other.energy.max = 5.0

            val subtract = actual - other
            subtract.shouldBeSubtractedOf(actual, other)
            subtract.hull.max shouldBe 5.0
            subtract.hull.current shouldBe 1.0
            subtract.shield.max shouldBe 5.0
            subtract.shield.current shouldBe 1.0
            subtract.energy.max shouldBe 5.0
            subtract.energy.current shouldBe 1.0
        }
    }

    expect("to SUBTRACT two StatBase, with max < current") {
        checkAll(StatBase.fixture, StatBase.fixture) { actual, other ->
            actual.hull.max = 10.0
            actual.hull.current = 10.0
            actual.shield.max = 10.0
            actual.shield.current = 10.0
            actual.energy.max = 10.0
            actual.energy.current = 10.0
            other.hull.max = 5.0
            other.shield.max = 5.0
            other.energy.max = 5.0

            val subtract = actual - other
            subtract.shouldBeSubtractedOf(actual, other)
            subtract.hull.max shouldBe 5.0
            subtract.hull.current shouldBe 5.0
            subtract.shield.max shouldBe 5.0
            subtract.shield.current shouldBe 5.0
            subtract.energy.max shouldBe 5.0
            subtract.energy.current shouldBe 5.0
        }
    }
})

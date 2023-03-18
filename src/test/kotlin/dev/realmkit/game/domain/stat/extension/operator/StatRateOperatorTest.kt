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

import dev.realmkit.game.domain.stat.document.StatRate
import dev.realmkit.game.domain.stat.extension.operator.StatRateOperator.minus
import dev.realmkit.game.domain.stat.extension.operator.StatRateOperator.plus
import dev.realmkit.hellper.extension.AssertionExtensions.shouldBeSubtractedOf
import dev.realmkit.hellper.extension.AssertionExtensions.shouldBeSumOf
import dev.realmkit.hellper.fixture.stat.fixture
import dev.realmkit.hellper.spec.TestSpec
import io.kotest.property.checkAll

class StatRateOperatorTest : TestSpec({
    expect("to SUM two StatRate") {
        checkAll(StatRate.fixture, StatRate.fixture) { actual, other ->
            val sum = actual + other
            sum.shouldBeSumOf(actual, other)
        }
    }

    expect("to SUBTRACT two StatRate") {
        checkAll(StatRate.fixture, StatRate.fixture) { actual, other ->
            val subtract = actual - other
            subtract.shouldBeSubtractedOf(actual, other)
        }
    }
})

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

package dev.realmkit.game.domain.stat.document

import dev.realmkit.hellper.fixture.stat.fixture
import dev.realmkit.hellper.spec.TestSpec
import io.kotest.matchers.doubles.shouldBePositive
import io.kotest.matchers.doubles.shouldBeZero
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.property.arbitrary.arbitrary

class StatBaseTest : TestSpec({
    context("unit testing StatBase") {
        expect("to create a new StatBase") {
            check(arbitrary { StatBase.fixture }) { base ->
                base.shouldNotBeNull()
                base.hull.current.shouldBePositive()
                base.hull.max.shouldBePositive()
                base.shield.current.shouldBePositive()
                base.shield.max.shouldBePositive()
                base.power.shouldBePositive()
                base.defense.shouldBePositive()
                base.speed.shouldBePositive()
            }
        }

        expect("to update a StatBase") {
            check(arbitrary { StatBase.fixture }) { stat ->
                stat.shouldNotBeNull()

                stat.hull.current.shouldBePositive()
                stat.hull.current = 0.0
                stat.hull.current.shouldBeZero()

                stat.hull.max.shouldBePositive()
                stat.hull.max = 0.0
                stat.hull.max.shouldBeZero()

                stat.shield.current.shouldBePositive()
                stat.shield.max.shouldBePositive()
                stat.power.shouldBePositive()
                stat.defense.shouldBePositive()
                stat.speed.shouldBePositive()
            }
        }
    }
})

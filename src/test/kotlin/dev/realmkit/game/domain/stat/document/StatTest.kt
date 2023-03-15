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

class StatTest : TestSpec({
    context("unit testing Stat") {
        expect("to create a new Stat") {
            check(arbitrary { Stat.fixture }) { stat ->
                stat.shouldNotBeNull()
                stat.base.hull.current.shouldBePositive()
                stat.base.hull.max.shouldBePositive()
                stat.base.shield.current.shouldBePositive()
                stat.base.shield.max.shouldBePositive()
                stat.base.power.shouldBePositive()
                stat.base.defense.shouldBePositive()
                stat.base.speed.shouldBePositive()
                stat.base.aggro.shouldBePositive()
                stat.rate.shieldRegeneration.shouldBePositive()
                stat.rate.critical.shouldBePositive()
                stat.multiplier.critical.shouldBePositive()
            }
        }

        expect("to update a Stat") {
            check(arbitrary { Stat.fixture }) { stat ->
                stat.shouldNotBeNull()

                stat.base.hull.current.shouldBePositive()
                stat.base.hull.current = 0.0
                stat.base.hull.current.shouldBeZero()

                stat.base.hull.max.shouldBePositive()
                stat.base.hull.max = 0.0
                stat.base.hull.max.shouldBeZero()

                stat.base.shield.current.shouldBePositive()
                stat.base.shield.max.shouldBePositive()
                stat.base.power.shouldBePositive()
                stat.base.defense.shouldBePositive()
                stat.base.speed.shouldBePositive()
                stat.base.aggro.shouldBePositive()
                stat.rate.shieldRegeneration.shouldBePositive()
                stat.rate.critical.shouldBePositive()
                stat.multiplier.critical.shouldBePositive()
            }
        }
    }
})

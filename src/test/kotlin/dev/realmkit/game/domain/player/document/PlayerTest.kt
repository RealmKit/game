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

package dev.realmkit.game.domain.player.document

import dev.realmkit.hellper.fixture.player.fixture
import dev.realmkit.hellper.spec.TestSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.doubles.shouldBePositive
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotBeEmpty
import io.kotest.property.arbitrary.arbitrary

class PlayerTest : TestSpec({
    context("unit testing Player") {
        expect("to create a new plain Player") {
            check(arbitrary { Player.fixture }) { player ->
                player.shouldNotBeNull()
                player.name.shouldNotBeNull().shouldNotBeEmpty()
                player.stat.shouldNotBeNull()
                player.stat.base.hull.current.shouldBePositive()
                player.stat.base.hull.max.shouldBePositive()
                player.stat.base.shield.max.shouldBePositive()
                player.stat.base.shield.max.shouldBePositive()
                player.stat.base.power.shouldBePositive()
                player.stat.base.defense.shouldBePositive()
                player.stat.base.speed.shouldBePositive()
                player.stat.rate.shieldRegeneration.shouldBePositive()
                player.stat.rate.critical.shouldBePositive()
                player.stat.multiplier.critical.shouldBePositive()
            }
        }

        expect("Player to be alive and dead") {
            check(arbitrary { Player.fixture }) { player ->
                player.alive.shouldBeTrue()
                player.stat.base.hull.current = 0.0
                player.alive.shouldBeFalse()
            }
        }
    }
})

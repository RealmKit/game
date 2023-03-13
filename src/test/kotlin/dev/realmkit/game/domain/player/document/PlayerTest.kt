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
import io.kotest.matchers.doubles.shouldBeLessThanOrEqual
import io.kotest.matchers.doubles.shouldBePositive
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotBeEmpty
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.checkAll

class PlayerTest : TestSpec({
    context("unit testing Player") {
        expect("to create a new plain Player") {
            check(arbitrary { Player.fixture }) { player ->
                player.shouldNotBeNull()
                player.name.shouldNotBeNull().shouldNotBeEmpty()
                player.stat.shouldNotBeNull()
                player.stat.hull.shouldBePositive()
                player.stat.shield.shouldBePositive()
                player.stat.power.shouldBePositive()
            }
        }

        expect("Player to calculate the damage output") {
            check(arbitrary { Player.fixture }) { player ->
                val damage = player.stat.power
                player.damage() shouldBe damage
            }
        }

        expect("Player to attack Target until dead") {
            checkAll(arbitrary { Player.fixture }, arbitrary { Player.fixture }) { player, target ->
                player.stat.hull.shouldBePositive()
                player.isAlive().shouldBeTrue()
                target.stat.hull.shouldBePositive()
                target.isAlive().shouldBeTrue()

                while (target.isAlive()) {
                    player attack target
                }

                player.stat.hull.shouldBePositive()
                player.isAlive().shouldBeTrue()
                target.stat.hull.shouldBeLessThanOrEqual(0.0)
                target.isAlive().shouldBeFalse()
            }
        }
    }
})

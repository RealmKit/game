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

import dev.realmkit.hellper.extension.AssertionExtensions.shouldBeAlive
import dev.realmkit.hellper.extension.AssertionExtensions.shouldNotBeAlive
import dev.realmkit.hellper.fixture.player.fixture
import dev.realmkit.hellper.spec.TestSpec
import io.kotest.matchers.doubles.shouldBePositive
import io.kotest.matchers.longs.shouldBePositive
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotBeEmpty

class PlayerTest : TestSpec({
    context("unit testing Player") {
        context("instantiate") {
            expect("to create a new plain Player") {
                check(Player.fixture) { player ->
                    player.shouldNotBeNull()
                    player.name.shouldNotBeNull().shouldNotBeEmpty()
                    player.stat.shouldNotBeNull()
                    player.stat.base.hull.current.shouldBePositive()
                    player.stat.base.hull.max.shouldBePositive()
                    player.stat.base.shield.current.shouldBePositive()
                    player.stat.base.shield.max.shouldBePositive()
                    player.stat.base.energy.current.shouldBePositive()
                    player.stat.base.energy.max.shouldBePositive()
                    player.stat.base.attack.shouldBePositive()
                    player.stat.base.defense.shouldBePositive()
                    player.stat.base.speed.shouldBePositive()
                    player.stat.base.aggro.shouldBePositive()
                    player.stat.rate.shieldRegeneration.shouldBePositive()
                    player.stat.rate.critical.shouldBePositive()
                    player.stat.multiplier.critical.shouldBePositive()
                    player.stat.progression.level.shouldBePositive()
                    player.stat.progression.experience.shouldBePositive()
                    player.resource.titanium.shouldBePositive()
                    player.resource.crystal.shouldBePositive()
                    player.resource.darkMatter.shouldBePositive()
                    player.resource.antiMatter.shouldBePositive()
                    player.resource.purunhalium.shouldBePositive()
                }
            }
        }

        context(".isAlive") {
            expect("Player to be alive and dead") {
                check(Player.fixture) { player ->
                    player.shouldBeAlive()
                    player.stat.base.hull.current = 0.0
                    player.shouldNotBeAlive()
                }
            }
        }
    }
})

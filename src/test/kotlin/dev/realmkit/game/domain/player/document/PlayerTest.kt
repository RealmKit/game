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
import io.kotest.property.checkAll

class PlayerTest : TestSpec({
    expect("to create a new Player") {
        checkAll(Player.fixture) { player ->
            player.shouldNotBeNull()
            player.name.shouldNotBeNull().shouldNotBeEmpty()
            player.ship.stat.shouldNotBeNull()
            player.ship.stat.base.hull.current.shouldBePositive()
            player.ship.stat.base.hull.max.shouldBePositive()
            player.ship.stat.base.shield.current.shouldBePositive()
            player.ship.stat.base.shield.max.shouldBePositive()
            player.ship.stat.base.energy.current.shouldBePositive()
            player.ship.stat.base.energy.max.shouldBePositive()
            player.ship.stat.base.attack.shouldBePositive()
            player.ship.stat.base.defense.shouldBePositive()
            player.ship.stat.base.speed.shouldBePositive()
            player.ship.stat.base.aggro.shouldBePositive()
            player.ship.stat.rate.shieldRegeneration.shouldBePositive()
            player.ship.stat.rate.critical.shouldBePositive()
            player.ship.stat.multiplier.critical.shouldBePositive()
            player.ship.stat.progression.level.shouldBePositive()
            player.ship.stat.progression.experience.shouldBePositive()
            player.resource.titanium.shouldBePositive()
            player.resource.crystal.shouldBePositive()
            player.resource.darkMatter.shouldBePositive()
            player.resource.antiMatter.shouldBePositive()
            player.resource.purunhalium.shouldBePositive()
        }
    }

    expect("Player to be alive and dead") {
        checkAll(Player.fixture) { player ->
            player.shouldBeAlive()
            player.ship.stat.base.hull.current = 0.0
            player.shouldNotBeAlive()
        }
    }
})

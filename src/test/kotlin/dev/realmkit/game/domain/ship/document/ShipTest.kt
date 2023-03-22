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

package dev.realmkit.game.domain.ship.document

import dev.realmkit.hellper.fixture.ship.ShipFixture.fixture
import dev.realmkit.hellper.spec.TestSpec
import io.kotest.matchers.doubles.shouldBePositive
import io.kotest.matchers.longs.shouldBePositive
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotBeEmpty
import io.kotest.property.checkAll

class ShipTest : TestSpec({
    expect("to create a new Ship") {
        checkAll(Ship.fixture) { ship ->
            ship.name.shouldNotBeNull().shouldNotBeEmpty()
            ship.stat.shouldNotBeNull()
            ship.stat.base.hull.current.shouldBePositive()
            ship.stat.base.hull.max.shouldBePositive()
            ship.stat.base.shield.current.shouldBePositive()
            ship.stat.base.shield.max.shouldBePositive()
            ship.stat.base.energy.current.shouldBePositive()
            ship.stat.base.energy.max.shouldBePositive()
            ship.stat.base.attack.shouldBePositive()
            ship.stat.base.defense.shouldBePositive()
            ship.stat.base.speed.shouldBePositive()
            ship.stat.base.aggro.shouldBePositive()
            ship.stat.rate.shieldRegeneration.shouldBePositive()
            ship.stat.rate.critical.shouldBePositive()
            ship.stat.multiplier.critical.shouldBePositive()
            ship.stat.progression.level.shouldBePositive()
            ship.stat.progression.experience.shouldBePositive()
            ship.stat.progression.points.shouldBePositive()
        }
    }
})

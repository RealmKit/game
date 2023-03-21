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

package dev.realmkit.hellper.fixture.ship

import dev.realmkit.game.domain.ship.document.Ship
import dev.realmkit.game.domain.stat.document.Stat
import dev.realmkit.game.domain.staticdata.enums.StaticDataShipEnum
import dev.realmkit.hellper.extension.AssertionExtensions.exhaustive
import dev.realmkit.hellper.extension.FakerExtensions.faker
import dev.realmkit.hellper.fixture.stat.StatFixture.fixture
import dev.realmkit.hellper.fixture.stat.StatFixture.invalid
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary

/**
 * # [ShipFixture]
 * contains all the [Ship] fixtures
 */
object ShipFixture {
    /**
     * ## [fixture]
     * creates a [Ship] with random data
     */
    val Ship.Companion.fixture: Arb<Ship>
        get() = arbitrary {
            Ship(
                type = exhaustive<StaticDataShipEnum>().toArb().bind(),
                name = arbitrary { faker.space.nasaSpaceCraft() }.bind(),
                stat = Stat.fixture.bind(),
            )
        }

    /**
     * ## [invalid]
     * creates a [Ship] with random invalid data
     */
    val Ship.Companion.invalid: Arb<Ship>
        get() = arbitrary {
            Ship(
                type = exhaustive<StaticDataShipEnum>().toArb().bind(),
                name = "",
                stat = Stat.invalid.bind(),
            )
        }
}

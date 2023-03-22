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

package dev.realmkit.hellper.fixture.player

import dev.realmkit.game.domain.player.document.Player
import dev.realmkit.game.domain.resource.document.Resource
import dev.realmkit.game.domain.ship.document.Ship
import dev.realmkit.hellper.extension.FakerExtensions.faker
import dev.realmkit.hellper.fixture.resource.ResourceFixture.fixture
import dev.realmkit.hellper.fixture.resource.ResourceFixture.invalid
import dev.realmkit.hellper.fixture.ship.ShipFixture.fixture
import dev.realmkit.hellper.fixture.ship.ShipFixture.invalid
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary

/**
 * # [PlayerFixture]
 * contains all the [Player] fixtures
 */
object PlayerFixture {
    /**
     * ## [fixture]
     * creates a [Player] with random data
     */
    val Player.Companion.fixture: Arb<Player>
        get() = arbitrary {
            Player(
                name = arbitrary { faker.superhero.name() }.bind(),
                ship = Ship.fixture.bind(),
                resource = Resource.fixture.bind(),
            )
        }

    /**
     * ## [invalid]
     * creates a [Player] with random invalid data
     */
    val Player.Companion.invalid: Arb<Player>
        get() = arbitrary {
            Player(
                name = "",
                ship = Ship.invalid.bind(),
                resource = Resource.invalid.bind(),
            )
        }
}

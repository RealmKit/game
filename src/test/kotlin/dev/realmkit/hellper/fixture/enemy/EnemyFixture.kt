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

package dev.realmkit.hellper.fixture.enemy

import dev.realmkit.game.domain.enemy.document.Enemy
import dev.realmkit.game.domain.ship.document.Ship
import dev.realmkit.hellper.extension.AssertionExtensions.DEFAULT_FIXTURES_SIZE
import dev.realmkit.hellper.extension.FakerExtensions.faker
import dev.realmkit.hellper.fixture.ship.ShipFixture.fixture
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary

/**
 * ## [EnemyFixture]
 * contains all the fixtures for [Enemy]
 */
object EnemyFixture {
    /**
     * ## [fixture]
     * creates a [Enemy] with random data
     */
    val Enemy.Companion.fixture: Arb<Enemy>
        get() = arbitrary {
            Enemy(
                name = arbitrary { faker.superhero.name() }.bind(),
                ship = Ship.fixture.bind(),
            )
        }

    /**
     * ## [many]
     * creates a [List] of [Enemy] with random data
     *
     * @param size the size of the list
     * @return [Arb] of [List] of [Enemy]
     */
    fun Enemy.Companion.many(size: Int = DEFAULT_FIXTURES_SIZE): Arb<List<Enemy>> =
        arbitrary {
            MutableList(size) { Enemy.fixture.bind() }
        }
}

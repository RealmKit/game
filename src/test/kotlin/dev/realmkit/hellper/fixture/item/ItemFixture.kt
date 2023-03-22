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

package dev.realmkit.hellper.fixture.item

import dev.realmkit.game.domain.item.document.Item
import dev.realmkit.game.domain.stat.document.Stat
import dev.realmkit.hellper.extension.FakerExtensions.faker
import dev.realmkit.hellper.fixture.stat.StatFixture.fixture
import dev.realmkit.hellper.fixture.stat.StatFixture.invalid
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary

/**
 * # [ItemFixture]
 * contains all the [Item] fixtures
 */
object ItemFixture {
    /**
     * ## [fixture]
     * creates a [Item] with random data
     */
    val Item.Companion.fixture: Arb<Item>
        get() = arbitrary {
            Item(
                name = arbitrary { faker.zelda.items() }.bind(),
                stat = Stat.fixture.bind(),
            )
        }

    /**
     * ## [invalid]
     * creates a [Item] with random invalid data
     */
    val Item.Companion.invalid: Arb<Item>
        get() = arbitrary {
            Item(
                name = "",
                stat = Stat.invalid.bind(),
            )
        }
}

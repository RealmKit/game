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

package dev.realmkit.test.sloth.testutils.fixture.player

import dev.realmkit.game.envy.domain.player.document.Player
import dev.realmkit.game.envy.domain.stat.document.Stat
import dev.realmkit.game.envy.domain.stat.extension.minus
import dev.realmkit.game.envy.domain.stat.extension.plus
import dev.realmkit.game.envy.domain.stat.property.NewbieStatProperties
import dev.realmkit.game.envy.domain.stat.property.StatProperties
import dev.realmkit.test.sloth.testutils.extensions.fake
import dev.realmkit.test.sloth.testutils.fixture.stat.arbitrary
import io.kotest.property.Arb
import io.kotest.property.Shrinker
import io.kotest.property.arbitrary.arbitrary

val Player.Companion.fixture: Player
    get() = fixture()

val Player.Companion.arbitrary: Arb<Player>
    get() = arbitrary(shrinker) {
        fixture(
            stat = StatProperties.arbitrary.bind(),
        )
    }

val Player.Companion.shrinker: Shrinker<Player>
    get() = Shrinker { player ->
        listOf(
            player.copy(stat = player.stat + 1),
            player.copy(stat = player.stat - 1),
        )
    }

/**
 * Creates a [Player] with random data
 *
 * @param name the [Player] `name`
 * @param stat the [Player.stat] `attributes`
 * @return the [Player]
 */
fun Player.Companion.fixture(
    name: String = fake.superhero.name(),
    stat: Stat = NewbieStatProperties.stat,
): Player = Player(
    name = name,
    stat = stat,
)

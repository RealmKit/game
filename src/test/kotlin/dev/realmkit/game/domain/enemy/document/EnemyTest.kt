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

package dev.realmkit.game.domain.enemy.document

import dev.realmkit.hellper.extension.AssertionExtensions.shouldBeAlive
import dev.realmkit.hellper.extension.AssertionExtensions.shouldNotBeAlive
import dev.realmkit.hellper.fixture.enemy.fixture
import dev.realmkit.hellper.spec.TestSpec
import io.kotest.matchers.doubles.shouldBePositive
import io.kotest.matchers.longs.shouldBePositive
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotBeEmpty
import io.kotest.property.checkAll

class EnemyTest : TestSpec({
    expect("to create a new plain Enemy") {
        checkAll(Enemy.fixture) { enemy ->
            enemy.shouldNotBeNull()
            enemy.name.shouldNotBeNull().shouldNotBeEmpty()
            enemy.ship.stat.shouldNotBeNull()
            enemy.ship.stat.base.hull.current.shouldBePositive()
            enemy.ship.stat.base.hull.max.shouldBePositive()
            enemy.ship.stat.base.shield.current.shouldBePositive()
            enemy.ship.stat.base.shield.max.shouldBePositive()
            enemy.ship.stat.base.energy.current.shouldBePositive()
            enemy.ship.stat.base.energy.max.shouldBePositive()
            enemy.ship.stat.base.attack.shouldBePositive()
            enemy.ship.stat.base.defense.shouldBePositive()
            enemy.ship.stat.base.speed.shouldBePositive()
            enemy.ship.stat.base.aggro.shouldBePositive()
            enemy.ship.stat.rate.shieldRegeneration.shouldBePositive()
            enemy.ship.stat.rate.critical.shouldBePositive()
            enemy.ship.stat.multiplier.critical.shouldBePositive()
            enemy.ship.stat.progression.level.shouldBePositive()
            enemy.ship.stat.progression.experience.shouldBePositive()
        }
    }

    expect("Enemy to be alive and dead") {
        checkAll(Enemy.fixture) { enemy ->
            enemy.shouldBeAlive()
            enemy.ship.stat.base.hull.current = 0.0
            enemy.shouldNotBeAlive()
        }
    }
})

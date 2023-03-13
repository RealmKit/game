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

package dev.realmkit.game.domain.player.service

import dev.realmkit.game.core.exception.ValidationException
import dev.realmkit.game.domain.player.document.Player
import dev.realmkit.hellper.extension.AssertionExtensions.shouldHaveAllErrors
import dev.realmkit.hellper.fixture.player.fixture
import dev.realmkit.hellper.infra.IntegrationTestContext
import dev.realmkit.hellper.spec.IntegrationTestSpec
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.doubles.shouldBePositive
import io.kotest.matchers.doubles.shouldBeZero
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.property.arbitrary.arbitrary

@IntegrationTestContext
class PlayerServiceTest(
    private val playerService: PlayerService,
) : IntegrationTestSpec({
    context("integration testing PlayerService") {
        expect("all beans to be inject") {
            playerService.shouldNotBeNull()
        }

        expect("it should create Players") {
            check(arbitrary { Player.fixture }) { player ->
                val saved = playerService new player.name
                saved.id.shouldNotBeNull()
                saved.createdAt.shouldNotBeNull()
                saved.updatedAt.shouldNotBeNull()
                saved.version.shouldNotBeNull()
                saved.name.shouldNotBeNull()
                saved.stat.base.hull.current.shouldBePositive()
                saved.stat.base.hull.max.shouldBePositive()
                saved.stat.base.shield.current.shouldBeZero()
                saved.stat.base.shield.max.shouldBeZero()
                saved.stat.base.power.shouldBePositive()
                saved.stat.base.defense.shouldBeZero()
                saved.stat.base.speed.shouldBePositive()
                saved.stat.rate.shieldRegeneration.shouldBeZero()
                saved.stat.rate.critical.shouldBeZero()
                saved.stat.multiplier.critical.shouldBePositive()
            }
        }

        context("Violations to be thrown") {
            expect("name should not be blank") {
                shouldThrow<ValidationException> {
                    playerService new ""
                }.shouldNotBeNull()
                    .invalid shouldHaveAllErrors listOf(
                    ".name" to "must not be blank",
                )
            }
        }
    }
})

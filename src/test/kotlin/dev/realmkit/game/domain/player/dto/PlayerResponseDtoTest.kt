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

package dev.realmkit.game.domain.player.dto

import dev.realmkit.game.domain.player.document.Player
import dev.realmkit.game.domain.player.extension.toDocument
import dev.realmkit.game.domain.player.extension.toResponseDto
import dev.realmkit.game.domain.stat.dto.StatProgressionResponseDto
import dev.realmkit.game.domain.stat.dto.StatResponseDto
import dev.realmkit.hellper.extension.fake
import dev.realmkit.hellper.fixture.player.arbitrary
import dev.realmkit.hellper.spec.TestSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

class PlayerResponseDtoTest : TestSpec({
    context("unit testing PlayerResponseDto") {
        expect("to create a new plain PlayerResponseDto") {
            val response = PlayerResponseDto(
                id = fake.random.nextUUID(),
                name = fake.superhero.name(),
                stat = StatResponseDto(
                    progression = StatProgressionResponseDto(
                        level = fake.random.nextLong(),
                        experience = fake.random.nextLong(),
                    ),
                ),
            ).shouldNotBeNull()
            response.id.shouldNotBeNull()
            response.name.shouldNotBeNull()
            response.stat.shouldNotBeNull()
            response.stat.progression.shouldNotBeNull()
            response.stat.progression.level.shouldNotBeNull()
            response.stat.progression.experience.shouldNotBeNull()
        }

        expect("to parse a Player to PlayerResponseDto") {
            check(Player.arbitrary) { player ->
                val response = player.toResponseDto
                response.name shouldBe player.name
            }
        }

        expect("to parse a PlayerCreateRequestDto to Player to PlayerResponseDto") {
            val request = PlayerCreateRequestDto(name = fake.superhero.name())
                .shouldNotBeNull()
            request.name.shouldNotBeNull()

            val player = request.toDocument
            player.name shouldBe request.name

            val response = player.toResponseDto
            response.name shouldBe request.name
        }
    }
})

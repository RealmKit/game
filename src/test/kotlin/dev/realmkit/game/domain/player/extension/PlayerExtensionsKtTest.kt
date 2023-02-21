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

package dev.realmkit.game.domain.player.extension

import dev.realmkit.game.domain.player.document.Player
import dev.realmkit.game.domain.player.dto.PlayerCreateRequestDto
import dev.realmkit.hellper.extension.fakeArb
import dev.realmkit.hellper.fixture.player.arbitrary
import dev.realmkit.hellper.spec.TestSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

class PlayerExtensionsKtTest : TestSpec({
    context("unit testing Player extensions") {
        expect("mapping to Response dto") {
            check(Player.arbitrary) { player ->
                val dto = player.toResponseDto
                    .shouldNotBeNull()
                dto.id shouldBe player.id
                dto.name shouldBe player.name
                dto.stat.progression.level shouldBe player.stat.progression.level
                dto.stat.progression.experience shouldBe player.stat.progression.experience
            }
        }

        expect("mapping to Document") {
            check(fakeArb.name) { name ->
                val player = PlayerCreateRequestDto(name = name).toDocument
                    .shouldNotBeNull()
                player.id.shouldNotBeNull()
                player.createdAt.shouldNotBeNull()
                player.updatedAt.shouldNotBeNull()
                player.name shouldBe name
                player.stat.shouldNotBeNull()
                player.stat.progression.shouldNotBeNull()
                player.stat.progression.level shouldBe 1
                player.stat.progression.experience shouldBe 0
            }
        }
    }
})

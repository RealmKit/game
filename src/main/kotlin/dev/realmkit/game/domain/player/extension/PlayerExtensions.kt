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
import dev.realmkit.game.domain.player.dto.PlayerResponseDto
import dev.realmkit.game.domain.stat.extension.toResponseDto

/**
 * ## [Player] -> [PlayerResponseDto]
 *
 * ```kotlin
 * import dev.realmkit.game.domain.player.extension.toResponseDto
 *
 * val player: Player = Player(name = "Player #1")
 * val dto: PlayerResponseDto = player.toResponseDto
 * ```
 *
 * @see PlayerResponseDto
 */
val Player.toResponseDto: PlayerResponseDto
    get() = PlayerResponseDto(
        id = id.toHexString(),
        name = name,
        stat = stat.toResponseDto,
    )

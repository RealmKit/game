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

import dev.realmkit.game.domain.player.document.Player
import dev.realmkit.game.domain.player.dto.PlayerResponseDto
import dev.realmkit.game.domain.player.extension.toResponseDto
import dev.realmkit.game.domain.player.repository.PlayerRepository
import org.springframework.stereotype.Service

/**
 * # [PlayerService]
 *
 * Executes all logics for [Player] document
 *
 * @property playerRepository the player document repository
 * @see Service
 */
@Service
class PlayerService(
    private val playerRepository: PlayerRepository,
) {
    /**
     * Creates a new [Player] within provided fields and persist it to DB
     *
     * ```kotlin
     * import dev.realmkit.game.domain.player.service.PlayerService
     *
     * class SomeController(
     *     // Inject the Service
     *     private val playerService: PlayerService,
     * ) {
     *     fun create(): PlayerResponseDto =
     *         playerRepository.new( "Player Number One" )
     * }
     * ```
     *
     * @param name the player name
     * @return the DTO from the persisted entity
     * @see Player
     */
    fun new(name: String): PlayerResponseDto {
        val player = Player(name = name)
        return playerRepository.save(player).toResponseDto
    }
}

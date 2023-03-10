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
import dev.realmkit.game.domain.base.extension.MongoRepositoryExtensions.persist
import dev.realmkit.game.domain.player.document.Player
import dev.realmkit.game.domain.player.extension.PlayerValidator.validated
import dev.realmkit.game.domain.player.repository.PlayerRepository
import dev.realmkit.game.domain.staticdata.service.StaticDataService
import io.konform.validation.Validation
import org.springframework.stereotype.Service

/**
 * # [PlayerService]
 * the [Player] service.
 *
 * @see Service
 *
 * @property playerRepository the player repository bean
 * @property staticDataService the stat service
 */
@Service
class PlayerService(
    private val playerRepository: PlayerRepository,
    private val staticDataService: StaticDataService,
) {
    /**
     * ## [new]
     * creates a new [Player] and persists it to DB, if valid
     * ```kotlin
     * playerService new "Player Number 1"
     * ```
     *
     * @see Player
     *
     * @param name the player name
     * @return the validated persisted document
     * @throws ValidationException if [Player] has [Validation] issues
     */
    @Throws(ValidationException::class)
    infix fun new(name: String): Player =
        this persist Player(
            name = name,
            stat = staticDataService.initial().stat,
        )

    /**
     * ## [persist]
     * validate and persists a [Player] to DB, if valid.
     * ```kotlin
     * playerService persist player
     * ```
     *
     * @see Player
     *
     * @param request the player to persist
     * @return the validated persisted document
     * @throws ValidationException if [Player] has [Validation] issues
     */
    @Throws(ValidationException::class)
    private infix fun persist(request: Player): Player =
        request validated { player ->
            playerRepository persist player
        }
}

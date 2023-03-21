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

import dev.realmkit.game.core.exception.NotFoundException
import dev.realmkit.game.core.exception.ValidationException
import dev.realmkit.game.domain.base.extension.MongoRepositoryExtensions.persist
import dev.realmkit.game.domain.player.document.Player
import dev.realmkit.game.domain.player.extension.PlayerValidator.validated
import dev.realmkit.game.domain.player.repository.PlayerRepository
import dev.realmkit.game.domain.stat.service.StatService
import dev.realmkit.game.domain.staticdata.enums.StaticDataShipEnum.BATTLE_WAR_SHIP_V1
import dev.realmkit.game.domain.staticdata.service.StaticDataService
import io.konform.validation.Validation
import org.springframework.stereotype.Service

/**
 * # [PlayerService]
 * the [Player] service
 *
 * @see Service
 *
 * @property playerRepository the player repository bean
 * @property staticDataService the static data service
 * @property statService the stat service
 */
@Service
class PlayerService(
    private val playerRepository: PlayerRepository,
    private val staticDataService: StaticDataService,
    private val statService: StatService,
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
            ship = staticDataService.ships(BATTLE_WAR_SHIP_V1),
            resource = staticDataService.resource(),
        )

    /**
     * ## [update]
     * updates a [Player] to DB, if valid
     * also level up it, if possible
     * ```kotlin
     * playerService update player
     * ```
     *
     * @see Player
     *
     * @param player the player to update
     * @return the validated persisted document
     * @throws ValidationException if [Player] has [Validation] issues
     * @throws NotFoundException if [Player.id] not found
     */
    @Throws(ValidationException::class, NotFoundException::class)
    infix fun update(player: Player): Player =
        find(player.id).let {
            statService levelUp player.ship.stat
            playerRepository persist player
        }

    /**
     * ## [find]
     * finds a [Player] by id, if exists, else throws [NotFoundException]
     * ```kotlin
     * playerService find "player-id"
     * ```
     *
     * @see Player
     *
     * @param id the player id
     * @return the found player
     * @throws NotFoundException if [Player] not found
     */
    @Throws(NotFoundException::class)
    private infix fun find(id: String): Player =
        playerRepository.findById(id)
            .orElseThrow { NotFoundException(Player::class, id) }

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

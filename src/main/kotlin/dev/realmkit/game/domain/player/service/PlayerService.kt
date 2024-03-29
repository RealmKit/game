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
import dev.realmkit.game.domain.item.document.Item
import dev.realmkit.game.domain.item.enums.ItemTypeEnum
import dev.realmkit.game.domain.item.service.ItemService
import dev.realmkit.game.domain.player.document.Player
import dev.realmkit.game.domain.player.extension.PlayerValidator.validated
import dev.realmkit.game.domain.player.repository.PlayerRepository
import dev.realmkit.game.domain.resource.service.ResourceService
import dev.realmkit.game.domain.ship.enums.ShipTypeEnum.BATTLE_WAR_SHIP_V1
import dev.realmkit.game.domain.ship.service.ShipService
import dev.realmkit.game.domain.stat.service.StatService
import io.konform.validation.Validation
import org.springframework.stereotype.Service

/**
 * # [PlayerService]
 * the [Player] service
 *
 * @see Service
 *
 * @property playerRepository the player repository bean
 * @property shipService the ship service
 * @property itemService the item service
 * @property resourceService the resource service
 * @property statService the stat service
 */
@Service
class PlayerService(
    private val playerRepository: PlayerRepository,
    private val shipService: ShipService,
    private val itemService: ItemService,
    private val resourceService: ResourceService,
    private val statService: StatService,
) {
    /**
     * ## [new]
     * creates a new [Player] and persists it to DB, if valid
     * ```kotlin
     * val newPlayer: Player = playerService new "Player Number 1"
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
            ship = shipService[BATTLE_WAR_SHIP_V1],
            resource = resourceService.resource,
        )

    /**
     * ## [update]
     * updates a [Player] to DB, if valid
     * also level up it, if possible
     * ```kotlin
     * val updated: Player = playerService update player
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
        find(player.id) {
            statService levelUp player.ship.stat
            playerRepository persist player
        }

    /**
     * ## [receive]
     * receives a new [Item] in [Player] inventory
     * ```kotlin
     * val updated: Player = playerService.receive(player = player, item = CHEAP_RECOVERY_DRONE)
     * ```
     *
     * @param player the player to receive the item
     * @param item the `item type` to add in [Player] inventory
     * @return the validated persisted document
     * @throws ValidationException if [Item] has [Validation] issues
     * @throws NotFoundException if [Player.id] not found
     */
    @Throws(ValidationException::class, NotFoundException::class)
    fun receive(player: Player, item: ItemTypeEnum): Item =
        find(player.id) {
            itemService.new(this, item)
        }

    /**
     * ## [use]
     * uses a [Item] from [Player] inventory, if available
     * ```kotlin
     * val updated: Player = playerService.use(player = player, item = CHEAP_RECOVERY_DRONE)
     * ```
     *
     * @param player the player to use the item
     * @param item the `item type` to use from [Player] inventory
     * @return the validated persisted document
     * @throws ValidationException if [Player] has [Validation] issues
     * @throws NotFoundException if [Player.id] not found
     * @throws NotFoundException if [Item] not found on [Player] inventory
     */
    @Throws(ValidationException::class, NotFoundException::class)
    fun use(player: Player, item: ItemTypeEnum): Player =
        find(player.id) {
            itemService.use(this, item)
            update(this)
        }

    /**
     * ## [find]
     * finds a [Player] by id, if exists, else throws [NotFoundException]
     * ```kotlin
     * val find: Player = playerService find "player-id"
     * ```
     *
     * @param id the player id
     * @param block the block to execute with the found player
     * @return the found player
     * @throws NotFoundException if [Player] not found
     */
    @Throws(NotFoundException::class)
    private fun <R> find(id: String, block: Player.() -> R): R =
        playerRepository.findById(id)
            .orElseThrow { NotFoundException(Player::class, id) }
            .let { player -> player.block() }

    /**
     * ## [persist]
     * validate and persists a [Player] to DB, if valid.
     * ```kotlin
     * val persisted: Player = playerService persist player
     * ```
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

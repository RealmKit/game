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

package dev.realmkit.game.domain.item.service

import dev.realmkit.game.core.exception.NotFoundException
import dev.realmkit.game.core.exception.ValidationException
import dev.realmkit.game.domain.base.extension.MongoRepositoryExtensions.persist
import dev.realmkit.game.domain.item.document.Item
import dev.realmkit.game.domain.item.extension.ItemValidator.validated
import dev.realmkit.game.domain.item.repository.ItemRepository
import dev.realmkit.game.domain.player.document.Player
import dev.realmkit.game.domain.player.service.PlayerService
import dev.realmkit.game.domain.stat.extension.operator.StatOperator.plusAssign
import dev.realmkit.game.domain.staticdata.enums.StaticDataItemEnum
import dev.realmkit.game.domain.staticdata.property.StaticDataProperties
import dev.realmkit.game.domain.target.document.Target
import io.konform.validation.Validation
import org.springframework.stereotype.Service

/**
 * # [ItemService]
 * the item service
 *
 * @see Item
 *
 * @param staticData the static data properties
 */
@Service
class ItemService(
    private val playerService: PlayerService,
    private val itemRepository: ItemRepository,
    private val staticData: StaticDataProperties,
) {
    /**
     * ## [get]
     * returns a copy of the item from the static data
     *
     * @param item the item to get
     * @return the item
     */
    operator fun get(item: StaticDataItemEnum): Item =
        staticData.items(item)

    /**
     * ## [new]
     * creates a new [Item] and persists it to DB, if valid
     * ```kotlin
     * itemService new (player to CHEAP_RECOVERY_DRONE)
     * ```
     *
     * @see Item
     *
     * @param pair the pair of owner and item to create
     * @return the validated persisted document
     * @throws ValidationException if [Item] has [Validation] issues
     */
    @Throws(ValidationException::class)
    infix fun new(pair: Pair<Target, StaticDataItemEnum>): Item =
        this persist get(pair.second)
            .apply { owner = pair.first.id }

    /**
     * ## [use]
     * uses an [Item] from the DB, if exists
     *
     * @see Item
     *
     * @param pair the pair of owner and item to use
     * @return the item
     * @throws ValidationException if [Player] has [Validation] issues after applying the [Item] stat
     */
    @Throws(ValidationException::class)
    infix fun use(pair: Pair<Player, StaticDataItemEnum>): Item =
        itemRepository.findAllByOwnerAndType(pair.first.id, pair.second)
            .ifEmpty { throw NotFoundException(Item::class, "Player ${pair.first.id} does not have any ${pair.second}") }
            .first()
            .also { item ->
                pair.first.ship.stat += item.stat
                playerService update pair.first
            }

    /**
     * ## [persist]
     * validate and persists a [Item] to DB, if valid.
     * ```kotlin
     * itemService persist item
     * ```
     *
     * @see Item
     *
     * @param request the item to persist
     * @return the validated persisted document
     * @throws ValidationException if [Item] has [Validation] issues
     */
    @Throws(ValidationException::class)
    private infix fun persist(request: Item): Item =
        request validated { item ->
            itemRepository persist item
        }
}

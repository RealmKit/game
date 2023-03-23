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
import dev.realmkit.game.domain.item.enums.ItemTypeEnum
import dev.realmkit.game.domain.item.extension.ItemValidator.validated
import dev.realmkit.game.domain.item.repository.ItemRepository
import dev.realmkit.game.domain.player.document.Player
import dev.realmkit.game.domain.stat.extension.operator.StatOperator.plusAssign
import dev.realmkit.game.domain.staticdata.property.StaticDataProperties
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
    private val itemRepository: ItemRepository,
    private val staticData: StaticDataProperties,
) {
    /**
     * ## [get]
     * returns a copy of the item from the static data
     * ```kotlin
     * val newItem = itemService[CHEAP_RECOVERY_DRONE]
     * ```
     *
     * @param item the item to get
     * @return the item
     */
    operator fun get(item: ItemTypeEnum): Item =
        staticData.items(item)

    /**
     * ## [new]
     * creates a new [Item] and persists it to DB, if valid
     * ```kotlin
     * val persistedItem = itemService.new(player=player, type=CHEAP_RECOVERY_DRONE)
     * ```
     *
     * @param player the player to create the item for
     * @param type the item type to create
     * @return the validated persisted document
     * @throws ValidationException if [Item] has [Validation] issues
     */
    @Throws(ValidationException::class)
    fun new(player: Player, type: ItemTypeEnum): Item =
        this persist get(type)
            .apply { owner = player.id }

    /**
     * ## [use]
     * uses an [Item] from the DB, if exists
     * ```kotlin
     * val usedItem = itemService.use(player=player, type=CHEAP_RECOVERY_DRONE)
     * ```
     *
     * @param player the player to use the item
     * @param type the item type to use
     * @return the item
     * @throws ValidationException if [Player] has [Validation] issues after applying the [Item] stat
     */
    @Throws(ValidationException::class)
    fun use(player: Player, type: ItemTypeEnum): Item =
        itemRepository.findAllByOwnerAndType(player.id, type)
            .ifEmpty { throw NotFoundException(Item::class, type) }
            .first()
            .also { item ->
                player.ship.stat += item.stat
                itemRepository.delete(item)
            }

    /**
     * ## [persist]
     * validate and persists a [Item] to DB, if valid.
     * ```kotlin
     * val persisted = itemService persist item
     * ```
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

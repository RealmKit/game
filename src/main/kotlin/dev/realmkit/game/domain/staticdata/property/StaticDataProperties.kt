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

package dev.realmkit.game.domain.staticdata.property

import dev.realmkit.game.core.extension.MapperExtensions.clone
import dev.realmkit.game.domain.item.document.Item
import dev.realmkit.game.domain.item.enums.ItemTypeEnum
import dev.realmkit.game.domain.resource.document.Resource
import dev.realmkit.game.domain.ship.document.Ship
import dev.realmkit.game.domain.ship.enums.ShipTypeEnum
import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * # [StaticDataProperties]
 * static data values
 *
 * @property config the static data config values
 * @property resource the initial resource properties
 * @property items the item static data values
 * @property ships the ships static data values
 */
@ConfigurationProperties(prefix = "app.static.data")
class StaticDataProperties(
    private val config: StaticDataConfig,
    private val resource: Resource,
    private val items: Map<ItemTypeEnum, Item>,
    private val ships: Map<ShipTypeEnum, Ship>,
) {
    /**
     * ## [config]
     * static data config values
     *
     * @return the static data config values
     */
    fun config(): StaticDataConfig = config

    /**
     * ## [resource]
     * initial resource properties
     *
     * @return the initial resource properties
     */
    fun resource(): Resource =
        resource.clone()

    /**
     * ## [ships]
     * [Ship] value property from the map
     *
     * @param ship the ship name
     * @return the [Ship] based on the name property
     */
    fun ships(ship: ShipTypeEnum): Ship =
        ships[ship]!!.clone()

    /**
     * ## [items]
     * [Item] value property from the map
     *
     * @param item the item name
     * @return the [Item] based on the name property
     */
    fun items(item: ItemTypeEnum): Item =
        items[item]!!.clone()
}

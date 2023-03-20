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
import dev.realmkit.game.domain.resource.document.Resource
import dev.realmkit.game.domain.ship.document.Ship
import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * # [StaticDataProperties]
 * static data values
 *
 * @property battleDuration the battle duration, in turns
 */
@ConfigurationProperties(prefix = "app.static.data")
class StaticDataProperties(
    private val battleDuration: Long,
    private val resource: Resource,
    private val battleWarShipV1: Ship,
) {
    /**
     * ## [battleDuration]
     * battle duration, in turns
     *
     * @return the battle duration, in turns
     */
    fun battleDuration(): Long =
        battleDuration

    /**
     * ## [resource]
     * initial resource properties
     *
     * @return the initial resource properties
     */
    fun resource(): Resource =
        resource.clone()

    /**
     * ## [battleWarShipV1]
     * initial battle war ship v1 properties
     *
     * @return the initial battle war ship v1 properties
     */
    fun battleWarShipV1(): Ship =
        battleWarShipV1.clone()
}

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

package dev.realmkit.game.envy.data.properties

import dev.realmkit.game.envy.domain.stat.property.StatsProperties

/**
 * [StaticProperties]
 * Defines some static values
 *
 * @see StatsProperties
 */
object StaticProperties {
    /**
     * [NewbieStaticProperties]
     * Defines some static values for Newbie Spec
     *
     * @see StaticProperties
     */
    object ZeroStaticProperties {
        const val BASE_STATIC_LEVEL = 0L
        const val BASE_STATIC_ZERO_VALUE = 0L
        const val BASE_STATIC_ZERO_MULTIPLIER = 0.0
    }

    object NewbieStaticProperties {
        const val BASE_NEWBIE_HEALTH = 10L
        const val BASE_NEWBIE_STAMINA = 10L
        const val BASE_NEWBIE_ATTACK = 1L
        const val BASE_NEWBIE_SPEED = 1L
        const val BASE_NEWBIE_DEFENSE = 1L
    }
}

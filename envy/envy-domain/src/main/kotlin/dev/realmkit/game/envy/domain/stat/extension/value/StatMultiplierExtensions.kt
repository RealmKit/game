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

package dev.realmkit.game.envy.domain.stat.extension.value

import dev.realmkit.game.envy.domain.stat.document.value.StatMultiplier

/**
 * Adds the `value` to the [StatMultiplier]
 *
 * @param value the `value` to sum
 * @return the [StatMultiplier] after adding the `value`
 */
operator fun StatMultiplier.plus(value: Int): StatMultiplier =
    copy(
        experience = experience + value,
        drop = drop + value,
        critical = critical + value,
    )

/**
 * Subtracts the `value` from the [StatMultiplier]
 *
 * @param value the `value` to subtracts
 * @return the [StatMultiplier] after subtracting the `value`
 */
operator fun StatMultiplier.minus(value: Int): StatMultiplier =
    copy(
        experience = experience - value,
        drop = drop - value,
        critical = critical - value,
    )
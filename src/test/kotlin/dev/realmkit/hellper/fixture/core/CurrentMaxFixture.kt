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

package dev.realmkit.hellper.fixture.core

import dev.realmkit.game.core.document.CurrentMax
import dev.realmkit.hellper.extension.RandomSourceExtensions.negativeDouble
import dev.realmkit.hellper.extension.RandomSourceExtensions.positiveDouble
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary

/**
 * # [CurrentMaxFixture]
 * contains all the [CurrentMax] fixtures
 */
object CurrentMaxFixture {
    /**
     * ## [fixture]
     * creates a [CurrentMax] with random data
     */
    val CurrentMax.Companion.fixture: Arb<CurrentMax>
        get() = arbitrary { rs ->
            val max = rs.positiveDouble()
            CurrentMax(
                max = max,
                current = max / 2,
            )
        }

    /**
     * ## [invalid]
     * creates a [CurrentMax] with random invalid data
     */
    val CurrentMax.Companion.invalid: Arb<CurrentMax>
        get() = arbitrary { rs ->
            val max = rs.negativeDouble()
            CurrentMax(
                max = max,
                current = max / 2,
            )
        }
}

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

package dev.realmkit.hellper.fixture.stat

import dev.realmkit.game.domain.stat.document.StatBase
import dev.realmkit.game.domain.stat.document.StatValue
import dev.realmkit.hellper.extension.RandomSourceExtensions.negativeDouble
import dev.realmkit.hellper.extension.RandomSourceExtensions.positiveDouble
import dev.realmkit.hellper.fixture.Fixture
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary

/**
 * Creates a [StatBase] with random data
 */
val StatBase.Companion.fixture: Arb<StatBase>
    get() = arbitrary { rs ->
        val hull = StatValue.fixture.bind()
        val shield = StatValue.fixture.bind()
        Fixture {
            StatBase::hull { hull }
            StatBase::shield { shield }
            StatBase::power { rs.positiveDouble() }
            StatBase::defense { rs.positiveDouble() }
            StatBase::speed { rs.positiveDouble() }
            StatBase::aggro { rs.positiveDouble() }
        }
    }

/**
 * Creates a [StatBase] with random invalid data
 */
val StatBase.Companion.invalid: Arb<StatBase>
    get() = arbitrary { rs ->
        val hull = StatValue.invalid.bind()
        val shield = StatValue.invalid.bind()
        Fixture {
            StatBase::hull { hull }
            StatBase::shield { shield }
            StatBase::power { rs.negativeDouble() }
            StatBase::defense { rs.negativeDouble() }
            StatBase::speed { rs.negativeDouble() }
            StatBase::aggro { rs.negativeDouble() }
        }
    }

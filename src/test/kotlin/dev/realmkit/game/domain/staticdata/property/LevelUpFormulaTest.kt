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

import dev.realmkit.hellper.spec.TestSpec
import io.kotest.matchers.shouldBe

class LevelUpFormulaTest : TestSpec({
    context("unit testing LevelUpFormula") {
        expect("instantiate a StaticData") {
            listOf(
                1L to 1L,
                2L to 8L,
                3L to 27L,
                4L to 64L,
                5L to 125L,
                6L to 216L,
                7L to 343L,
                8L to 512L,
                9L to 729L,
                10L to 1_000L,
                20L to 8_000L,
                50L to 125_000L,
                99L to 970_299L,
                100L to 1_000_000L,
                101L to 1_030_301L,
                200L to 8_000_000L,
                1_000L to 1_000_000_000L,
            ).forEach {
                LevelUpFormula(it.first) shouldBe it.second
            }
        }
    }
})

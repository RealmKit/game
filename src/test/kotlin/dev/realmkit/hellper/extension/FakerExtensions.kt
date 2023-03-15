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

package dev.realmkit.hellper.extension

import io.github.serpro69.kfaker.Faker
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import kotlin.random.Random
import kotlin.random.nextLong

/**
 * # [FakerExtensions]
 */
object FakerExtensions {
    val faker: Faker = Faker()
    val positiveLong: Long
        get() = Random.nextLong(1L until Long.MAX_VALUE)
    val negativeLong: Long
        get() = Random.nextLong(Long.MIN_VALUE until 0)
    val positiveDouble: Double
        get() = faker.random.nextDouble()
    val negativeDouble: Double
        get() = positiveDouble * -1
    val name: Arb<String>
        get() = arbitrary { faker.superhero.name() }
}

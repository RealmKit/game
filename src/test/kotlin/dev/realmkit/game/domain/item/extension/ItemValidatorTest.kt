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

package dev.realmkit.game.domain.item.extension

import dev.realmkit.game.core.exception.ValidationException
import dev.realmkit.game.domain.item.document.Item
import dev.realmkit.game.domain.item.extension.ItemValidator.validated
import dev.realmkit.hellper.extension.AssertionExtensions.shouldHaveAllErrors
import dev.realmkit.hellper.fixture.item.ItemFixture.fixture
import dev.realmkit.hellper.fixture.item.ItemFixture.invalid
import dev.realmkit.hellper.spec.TestSpec
import io.kotest.assertions.konform.shouldBeInvalid
import io.kotest.assertions.konform.shouldBeValid
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.property.checkAll

class ItemValidatorTest : TestSpec({
    expect("to validate a Item") {
        checkAll(Item.fixture) { item ->
            item validated { validated -> validated.shouldNotBeNull() }
        }
    }

    expect("Item to be valid") {
        checkAll(Item.fixture) { item ->
            ItemValidator.validation shouldBeValid item
        }
    }

    expect("to throw a ValidationException when validating a Item") {
        checkAll(Item.invalid) { item ->
            shouldThrow<ValidationException> {
                item validated { null }
            }.shouldNotBeNull()
                .invalid.shouldNotBeNull()
                .errors.shouldNotBeNull().shouldNotBeEmpty()
        }
    }

    expect("Item to be invalid") {
        checkAll(Item.invalid) { item ->
            ItemValidator.validation.shouldBeInvalid(item) { invalid ->
                invalid shouldHaveAllErrors listOf(
                    ".owner" to "must not be blank",
                    ".name" to "must not be blank",
                )
            }
        }
    }
})

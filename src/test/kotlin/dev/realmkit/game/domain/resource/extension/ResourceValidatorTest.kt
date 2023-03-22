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

package dev.realmkit.game.domain.resource.extension

import dev.realmkit.game.domain.resource.document.Resource
import dev.realmkit.hellper.extension.AssertionExtensions.shouldHaveAllErrors
import dev.realmkit.hellper.fixture.resource.ResourceFixture.fixture
import dev.realmkit.hellper.fixture.resource.ResourceFixture.invalid
import dev.realmkit.hellper.spec.TestSpec
import io.kotest.assertions.konform.shouldBeInvalid
import io.kotest.assertions.konform.shouldBeValid
import io.kotest.property.checkAll

class ResourceValidatorTest : TestSpec({
    expect("resource to be valid") {
        checkAll(Resource.fixture) { resource ->
            ResourceValidator.validation shouldBeValid resource
        }
    }

    expect("resource to be invalid") {
        checkAll(Resource.invalid) { resource ->
            ResourceValidator.validation.shouldBeInvalid(resource) { invalid ->
                invalid shouldHaveAllErrors listOf(
                    ".titanium" to "must be positive",
                    ".crystal" to "must be positive",
                    ".darkMatter" to "must be positive",
                    ".antiMatter" to "must be positive",
                    ".purunhalium" to "must be positive",
                )
            }
        }
    }
})

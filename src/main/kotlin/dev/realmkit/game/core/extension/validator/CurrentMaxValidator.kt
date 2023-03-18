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

package dev.realmkit.game.core.extension.validator

import dev.realmkit.game.core.extension.ValidationExtensions.positive
import dev.realmkit.game.domain.aliases.CurrentMaxDouble
import io.konform.validation.Validation

/**
 * # [CurrentMaxValidator]
 * [dev.realmkit.game.core.document.CurrentMax] validations
 */
object CurrentMaxValidator {
    /**
     * ## [validation]
     * [dev.realmkit.game.core.document.CurrentMax] -> [Validation] object
     */
    val validation: Validation<CurrentMaxDouble> = Validation {
        CurrentMaxDouble::current required {}
        CurrentMaxDouble::max required { positive() }
        addConstraint(".current must be lower than .max") {
            it.current <= it.max
        }
    }
}

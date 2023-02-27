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

package dev.realmkit.game.domain.base.extension

import dev.realmkit.game.domain.base.exception.violation.BlankViolation
import dev.realmkit.game.domain.base.exception.violation.GenericViolation
import dev.realmkit.game.domain.base.exception.violation.Violation
import kotlin.jvm.internal.PropertyReference0
import kotlin.reflect.KClass
import kotlin.reflect.KProperty0

/**
 * # Validation Context
 */
class ValidationContext {
    /**
     * violations the Violations map
     */
    val violations: HashMap<String, Violation> = hashMapOf()

    /**
     * ## [generic]
     *
     * If a generic problem is found, then save to the violations map
     *
     * @param problem the generic exception
     * @return nothing
     */
    fun generic(problem: Throwable?) =
        problem?.let {
            violations["generic"] = GenericViolation(problem = problem)
        }

    /**
     * ## [isBlank]
     *
     * Check if the data is blank
     *
     * @param field the property
     * @return nothing
     */
    fun isBlank(field: KProperty0<String?>) =
        takeIf { field.get()?.isBlank() ?: false }?.let {
            violations[field.name] = BlankViolation(
                owner = ((field as? PropertyReference0)?.owner as? KClass<*>)?.simpleName,
                field = field.name,
            )
        }
}

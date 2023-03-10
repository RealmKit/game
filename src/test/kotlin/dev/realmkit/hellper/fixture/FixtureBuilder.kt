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

package dev.realmkit.hellper.fixture

import dev.realmkit.game.core.extension.MapperExtensions.mapper
import org.json.JSONObject
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

/**
 * # [FixtureBuilder]
 * The fixture builder context handler
 *
 * @property type the class type
 * @property properties the mapped properties values
 * @see Fixture
 */
data class FixtureBuilder<T : Any>(
    private val type: KClass<T>,
    private val properties: MutableMap<String, Any?> = hashMapOf(),
) {
    /**
     * ## [invoke]
     * Maps the value to the property
     *
     * @param block the code block
     * @return the object value
     */
    operator fun <R : Any> KProperty1<T, R>.invoke(block: (R?) -> R): R =
        block(null)
            .also { value -> properties[name] = map(value) }

    /**
     * ## [build]
     * Build the object data
     *
     * @return the object
     */
    fun build(): T =
        JSONObject(properties)
            .run { mapper.readValue(toString(), type.java) }

    /**
     * ## [from]
     * Gets the value from the property if possible
     *
     * @param value the object to map from
     * @return the random data object
     */
    private fun <T, R> KProperty1<T, R?>.from(value: T): Any? =
        get(value)?.let { objValue ->
            if (objValue::class.isData) {
                map(objValue)
            } else {
                objValue
            }
        }

    /**
     * ## [map]
     * Map the object value if possible
     *
     * @param value the object to map
     * @return the object data
     */
    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> map(value: T): Any =
        (value::class as? KClass<T>)
            ?.takeIf { type -> type.isData }
            ?.memberProperties
            ?.associate { prop -> prop.name to prop.from(value) }
            ?: value
}

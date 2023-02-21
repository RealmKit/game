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

package dev.realmkit.game.domain.base.document

import dev.realmkit.game.core.extension.now
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

/**
 * # [BaseDocument]
 *
 * Defines default and audit properties for Documents.
 * Others documents should inherit from this class.
 *
 * ```kotlin
 * @Document
 * data class SomeDocument(
 *     val someProperty: String,
 * ) : BaseDocument() {
 *     companion object
 * }
 * ```
 *
 * @see Document
 */
open class BaseDocument {
    /**
     * Audit property for tracking the creation time of the document.
     * Should not be updated.
     */
    var createdAt: Instant = now

    /**
     * Audit property for tracking the updating time of the document.
     * Should be updated everytime an update is made on the document.
     */
    var updatedAt: Instant = now

    /**
     * ID property used to query the document.
     */
    @Id
    var id: String = ObjectId.get().toHexString()
}

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

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

/**
 * # [BaseDocument]
 * defines default and audit properties for Documents
 * others documents should inherit from this class
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
     * document ID property
     */
    @Id
    @JsonIgnore
    lateinit var id: String

    /**
     * document creation time
     */
    @CreatedDate
    @JsonIgnore
    lateinit var createdAt: Instant

    /**
     * document update time
     */
    @LastModifiedDate
    @JsonIgnore
    lateinit var updatedAt: Instant

    /**
     * document version
     */
    @Version
    @JsonIgnore
    lateinit var version: String
}

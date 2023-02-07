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

package dev.realmkit.test.sloth.testutils.infra

import com.mongodb.client.MongoClients
import dev.realmkit.game.sloth.core.extensions.ifTrue
import org.springframework.data.mongodb.core.MongoTemplate
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container

object Mongo {
    @JvmStatic
    @Container
    private val container = MongoDBContainer("mongo:latest")
    private val mongoTemplate by lazy {
        MongoTemplate(MongoClients.create(container.replicaSetUrl), "realmkit-game")
    }

    /**
     * Starts the [MongoDB container][MongoDBContainer]
     *
     * @return [MongoDBContainer] after started
     * @see MongoDBContainer
     */
    fun start() = container.takeUnless { it.isRunning }?.start().let { container }

    /**
     * Stops the [MongoDB container][MongoDBContainer]
     *
     * @see MongoDBContainer
     */
    fun stop() = container.takeIf { it.isRunning }?.stop()

    /**
     * Clears the [MongoDB][MongoTemplate] collections
     *
     * @return nothing
     * @see MongoTemplate
     */
    fun clear() = container.isRunning.ifTrue { mongoTemplate.db.drop() }
}

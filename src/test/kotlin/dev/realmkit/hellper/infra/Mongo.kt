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

package dev.realmkit.hellper.infra

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.testcontainers.containers.MongoDBContainer

/**
 * # [Mongo]
 * Wraps the [MongoDB][MongoTemplate] container
 */
object Mongo {
    private const val MONGO_DOCKER_IMAGE = "mongo:latest"
    private val container by lazy {
        MongoDBContainer(MONGO_DOCKER_IMAGE).apply {
            start()
        }
    }

    /**
     * Clears the [MongoDB][MongoTemplate] collections
     *
     * @param registry the [property registry][DynamicPropertyRegistry]
     * @return nothing
     * @see DynamicPropertyRegistry
     */
    fun registry(registry: DynamicPropertyRegistry) =
        registry.add("spring.data.mongodb.uri") { container.replicaSetUrl }
}

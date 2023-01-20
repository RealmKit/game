package dev.realmkit.test.sloth.testutils.infra

import com.mongodb.client.MongoClients
import org.springframework.data.mongodb.core.MongoTemplate
import org.testcontainers.containers.MongoDBContainer

object MongoDB {
    private val mongo = MongoDBContainer("mongo:latest")
        .apply { portBindings = listOf("27018:27017") }

    private val mongoTemplate by lazy {
        MongoTemplate(MongoClients.create("mongodb://localhost:27018"), "test")
    }

    val start
        get() = mongo.takeUnless { it.isRunning }?.start()

    val stop
        get() = mongo.takeIf { it.isRunning }?.stop()

    val clear
        get() = mongo.takeIf { it.isRunning }?.let { mongoTemplate.db.drop() }
}
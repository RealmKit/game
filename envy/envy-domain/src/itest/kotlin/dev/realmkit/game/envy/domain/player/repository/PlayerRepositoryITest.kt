package dev.realmkit.game.envy.domain.player.repository

import dev.realmkit.test.sloth.testutils.specs.TestSpec
import io.kotest.core.extensions.ApplyExtension
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.matchers.longs.shouldBeZero
import io.kotest.matchers.nulls.shouldNotBeNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.MongoDBContainer

@DataMongoTest
@ActiveProfiles("itest")
@ApplyExtension(SpringTestExtension::class)
@ContextConfiguration(classes = [TestApplication::class])
class PlayerRepositoryITest(
    private val playerRepository: PlayerRepository,
) : TestSpec({
    MongoDBContainer("mongo:latest")
        .apply { portBindings = listOf("27018:27017") }
        .start()

//    val mongoTemplate by lazy {
//        MongoTemplate(MongoClients.create("mongodb://localhost:27018"), "test")
//    }

    expect("all beans to be inject") {
        playerRepository.shouldNotBeNull()
    }

    expect("it should be empty") {
        withContext(Dispatchers.IO) {
            playerRepository.count().shouldBeZero()
        }
    }
})

@SpringBootApplication
class TestApplication
package dev.realmkit.test.sloth.testutils.infra

import io.kotest.core.extensions.ApplyExtension
import io.kotest.extensions.spring.SpringTestExtension
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@SpringBootApplication
@EnableMongoRepositories("dev.realmkit.game.envy.domain")
class SpringBootTestApplication

@DataMongoTest
@ActiveProfiles("itest")
@ApplyExtension(SpringTestExtension::class)
@ContextConfiguration(classes = [SpringBootTestApplication::class])
annotation class ITestContext
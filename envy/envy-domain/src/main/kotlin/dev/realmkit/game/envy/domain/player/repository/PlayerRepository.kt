package dev.realmkit.game.envy.domain.player.repository

import dev.realmkit.game.envy.domain.player.document.Player
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PlayerRepository : MongoRepository<Player, ObjectId>
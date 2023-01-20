package dev.realmkit.test.sloth.testutils.fixture.player

import dev.realmkit.game.envy.domain.player.document.Player
import dev.realmkit.test.sloth.testutils.extensions.fake
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary

val Player.Companion.fixture: Player
    get() = Player(
        name = fake.superhero.name()
    )

val Player.Companion.arbitrary: Arb<Player>
    get() = arbitrary { Player.fixture }
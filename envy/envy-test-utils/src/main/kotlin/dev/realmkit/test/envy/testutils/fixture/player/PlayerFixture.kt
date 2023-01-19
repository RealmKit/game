package dev.realmkit.test.envy.testutils.fixture.player

import dev.realmkit.game.envy.domain.player.document.Player
import dev.realmkit.test.sloth.testutils.extensions.fake

val Player.Companion.fixture
    get() = Player(
        name = fake.superhero.name()
    )
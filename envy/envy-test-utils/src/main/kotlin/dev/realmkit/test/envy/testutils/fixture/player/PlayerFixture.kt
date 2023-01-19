package dev.realmkit.test.envy.testutils.fixture.player

import dev.realmkit.game.envy.domain.player.document.Player

val Player.Companion.fixture
    get() = Player(
        name = ""
    )
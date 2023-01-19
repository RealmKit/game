package dev.realmkit.game.envy.domain.player.document

import dev.realmkit.test.envy.testutils.fixture.player.fixture
import dev.realmkit.test.sloth.testutils.specs.TestSpec
import io.kotest.assertions.asClue
import io.kotest.matchers.nulls.shouldNotBeNull

class PlayerTest : TestSpec({
    expect("to create a new plain Player") {
        Player.fixture
            .shouldNotBeNull()
            .asClue { player ->
                player.name.shouldNotBeNull()
            }
    }
})

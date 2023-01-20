package dev.realmkit.game.envy.core.extensions

import dev.realmkit.game.sloth.core.extensions.ifTrue
import dev.realmkit.test.sloth.testutils.specs.TestSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull

class BooleanExtensionsKtTest : TestSpec({
    expect("that Boolean.ifTrue should call a function") {
        true.ifTrue { true }.shouldNotBeNull().shouldBeTrue()
    }

    expect("that Boolean.ifTrue should not call a function") {
        false.ifTrue { true }.shouldBeNull()
    }
})

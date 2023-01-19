package dev.realmkit.test.helpers.specs

import io.kotest.core.spec.style.ExpectSpec

abstract class TestSpec(body: TestSpec.() -> Unit = {}) : ExpectSpec() {
    init {
        body()
    }
}

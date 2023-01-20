package dev.realmkit.test.sloth.testutils.specs

import dev.realmkit.test.sloth.testutils.infra.MongoDB
import io.kotest.core.spec.Spec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.property.Arb
import io.kotest.property.checkAll
import kotlinx.coroutines.coroutineScope

abstract class ITestSpec(body: ITestSpec.() -> Unit = {}) : TestSpec() {
    init {
        body()
    }

    override suspend fun beforeSpec(spec: Spec) {
        MongoDB.start
    }

    override fun afterSpec(f: suspend (Spec) -> Unit) {
        MongoDB.stop
    }

    override suspend fun afterEach(testCase: TestCase, result: TestResult) {
        MongoDB.clear
    }

    suspend fun context(function: () -> Unit) =
        coroutineScope { function() }

    suspend fun <T> check(arbitrary: Arb<T>, block: (T) -> Unit) {
        checkAll(arbitrary) { arb ->
            context {
                block(arb)
            }
        }
    }
}

package dev.realmkit

import com.tngtech.archunit.junit.AnalyzeClasses
import dev.realmkit.test.sloth.testutils.specs.ArchTestSpec

@AnalyzeClasses(packages = ["dev.realmkit.game"])
class EnvyDomainArchTest : ArchTestSpec()
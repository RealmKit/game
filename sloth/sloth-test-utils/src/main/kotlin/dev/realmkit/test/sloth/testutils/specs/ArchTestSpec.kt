package dev.realmkit.test.sloth.testutils.specs

import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noMethods
import com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS
import com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION
import com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING
import com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JODATIME
import com.tngtech.archunit.library.GeneralCodingRules.testClassesShouldResideInTheSamePackageAsImplementation

abstract class ArchTestSpec(body: ArchTestSpec.() -> Unit = {}) : TestSpec() {
    init {
        body()
    }

    @ArchTest
    val noClassesShouldThrowGenericExceptions: ArchRule =
        NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS

    @ArchTest
    val noClassesShouldUseJavaUtilLogging: ArchRule =
        NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING

    @ArchTest
    val noClassesShouldUseJodaTime: ArchRule =
        NO_CLASSES_SHOULD_USE_JODATIME

    @ArchTest
    val noClassesShouldUseFieldInjectionAnnotation: ArchRule =
        NO_CLASSES_SHOULD_USE_FIELD_INJECTION

    @ArchTest
    val testClassesShouldResideInTheSamePackageAsImplementation: ArchRule =
        testClassesShouldResideInTheSamePackageAsImplementation()

    @ArchTest
    val noMethodsShouldHaveRawReturnType: ArchRule =
        noMethods()
            .should().haveRawReturnType(List::class.java)
            .orShould().haveRawReturnType(Set::class.java)
            .orShould().haveRawReturnType(Map::class.java)
}

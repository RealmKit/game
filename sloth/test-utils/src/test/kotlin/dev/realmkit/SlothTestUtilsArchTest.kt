package dev.realmkit

import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noMethods
import com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS
import com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION
import com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING
import com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JODATIME
import com.tngtech.archunit.library.GeneralCodingRules.testClassesShouldResideInTheSamePackageAsImplementation

@AnalyzeClasses(packages = ["dev.realmkit.game"])
class SlothTestUtilsArchTest {
    @ArchTest
    private val noClassesShouldThrowGenericExceptions =
        NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS

    @ArchTest
    private val noClassesShouldUseJavaUtilLogging =
        NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING

    @ArchTest
    private val noClassesShouldUseJodaTime =
        NO_CLASSES_SHOULD_USE_JODATIME

    @ArchTest
    private val noClassesShouldUseFieldInjectionAnnotation =
        NO_CLASSES_SHOULD_USE_FIELD_INJECTION

    @ArchTest
    private val testClassesShouldResideInTheSamePackageAsImplementation =
        testClassesShouldResideInTheSamePackageAsImplementation()

    @ArchTest
    private val noMethodsShouldHaveRawReturnType =
        noMethods()
            .should().haveRawReturnType(List::class.java)
            .orShould().haveRawReturnType(Set::class.java)
            .orShould().haveRawReturnType(Map::class.java)
}

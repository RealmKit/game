/*
 * Copyright (c) 2023 RealmKit
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package dev.realmkit.test.sloth.testutils.specs

import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noMethods
import com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS
import com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION
import com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING
import com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JODATIME
import com.tngtech.archunit.library.GeneralCodingRules.testClassesShouldResideInTheSamePackageAsImplementation

/**
 * A [ArchTestSpec] extends [TestSpec] with all default Arch Test suite case
 */
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

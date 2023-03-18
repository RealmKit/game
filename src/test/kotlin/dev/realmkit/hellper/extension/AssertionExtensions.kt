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

package dev.realmkit.hellper.extension

import dev.realmkit.game.core.extension.ConstantExtensions.ZERO
import dev.realmkit.game.domain.aliases.CurrentMaxDouble
import dev.realmkit.game.domain.battle.action.BattleAction
import dev.realmkit.game.domain.battle.context.BattleContextResult
import dev.realmkit.game.domain.stat.document.Stat
import dev.realmkit.game.domain.stat.document.StatBase
import dev.realmkit.game.domain.stat.document.StatMultiplier
import dev.realmkit.game.domain.stat.document.StatProgression
import dev.realmkit.game.domain.stat.document.StatRate
import dev.realmkit.game.domain.target.document.Target
import dev.realmkit.hellper.extension.AssertionExtensions.shouldBeSumOf
import dev.realmkit.hellper.extension.AssertionExtensions.shouldHaveErrors
import io.konform.validation.Invalid
import io.konform.validation.ValidationError
import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.doubles.shouldBeGreaterThanOrEqual
import io.kotest.matchers.doubles.shouldBeLessThanOrEqual
import io.kotest.matchers.doubles.shouldBePositive
import io.kotest.matchers.doubles.shouldNotBeGreaterThan
import io.kotest.matchers.doubles.shouldNotBeLessThan
import io.kotest.matchers.ints.shouldBeGreaterThanOrEqual
import io.kotest.matchers.longs.shouldBeGreaterThanOrEqual
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf

/**
 * # [Violation]
 * violation pair for [ValidationError.dataPath] to [ValidationError.message]
 */
typealias Violation = Pair<String, String>

/**
 * # [AssertionExtensions]
 */
object AssertionExtensions {
    /**
     * ## [shouldHaveAllErrors]
     *
     * @param violations the list of violations pair for [ValidationError.dataPath] to [ValidationError.message]
     * @return the validation
     */
    infix fun Invalid<*>.shouldHaveAllErrors(violations: List<Violation>) {
        violations.forEach { violation ->
            this shouldContainFieldError violation
        }
        this shouldHaveErrors violations.size
    }

    /**
     * ## [shouldContainFieldError]
     *
     * @param violation the error pair for [ValidationError.dataPath] to [ValidationError.message]
     * @return the validation
     */
    private infix fun Invalid<*>.shouldContainFieldError(violation: Violation) =
        errors.first { it.dataPath == violation.first }
            .shouldNotBeNull()
            .message shouldBe violation.second

    /**
     * ## [shouldHaveErrors]
     *
     * @param size the number of validation errors
     * @return the validation
     */
    private infix fun Invalid<*>.shouldHaveErrors(size: Int) =
        errors shouldHaveSize size

    /**
     * ## [shouldBeAlive]
     * check if the [Target] is alive
     *
     * @see Target.alive
     *
     * @return the [Target] being validated
     */
    fun Target.shouldBeAlive(): Target = asClue {
        withClue("alive") { alive.shouldBeTrue() }
        withClue(".stat.base.shield.current") { stat.base.shield.current.shouldBeGreaterThanOrEqual(ZERO) }
        withClue(".stat.base.hull.current") { stat.base.hull.current.shouldBePositive() }
        this
    }

    /**
     * ## [shouldNotBeAlive]
     * check if the [Target] is dead
     *
     * @see Target.alive
     *
     * @return the [Target] being validated
     */
    fun Target.shouldNotBeAlive(): Target = asClue {
        withClue("alive") { alive.shouldBeFalse() }
        withClue(".stat.base.hull.current") { stat.base.hull.current.shouldBeLessThanOrEqual(ZERO) }
        this
    }

    /**
     * ## [shouldHaveTurns]
     * check if the [BattleContextResult] has the expected number of turns
     *
     * @param size the number of turns
     * @return the battle result
     */
    fun BattleContextResult?.shouldHaveTurns(size: Long): BattleContextResult = asClue {
        shouldNotBeNull()
        turns.shouldBe(size)
        logsPerTurn.shouldHaveSize(size.toInt())
        this
    }

    /**
     * ## [onTurn]
     * check what happens in a turn
     *
     * @param turn the turn number
     * @param actions the number of actions
     * @param block the block to check the actions
     * @return the battle result
     */
    fun BattleContextResult?.onTurn(
        turn: Long,
        actions: Long,
        block: Iterator<BattleAction>.() -> Unit,
    ): BattleContextResult {
        shouldNotBeNull()
        turns.shouldBeGreaterThanOrEqual(turn)
        logsPerTurn.size.shouldBeGreaterThanOrEqual(turn.toInt())
        logsPerTurn[turn].shouldNotBeNull()
            .shouldHaveSize(actions.toInt())
            .iterator()
            .block()
        return this
    }

    /**
     * ## [onAction]
     * check what happens in an action and step to the next action
     *
     * @param block the block to check the action
     * @return the battle result
     */
    inline fun <reified T : BattleAction> Iterator<BattleAction>.onAction(block: T.() -> Unit): Iterator<BattleAction> {
        shouldNotBeNull()
        hasNext().shouldBeTrue()
        next().shouldNotBeNull().shouldBeTypeOf<T>().apply(block)
        return this
    }

    /**
     * ## [shouldBeSumOf]
     * check if the [StatBase] is the result of adding the [other] to [actual]
     *
     * @param actual the [StatBase] to add to
     * @param other the [StatBase] to add
     * @return the [StatBase] being validated
     */
    fun Stat.shouldBeSumOf(actual: Stat, other: Stat): Stat = asClue {
        base.shouldBeSumOf(actual.base, other.base)
        rate.shouldBeSumOf(actual.rate, other.rate)
        multiplier.shouldBeSumOf(actual.multiplier, other.multiplier)
        progression.shouldBeSumOf(actual.progression, other.progression)
        this
    }

    /**
     * ## [shouldBeSumOf]
     * check if the [StatBase] is the result of adding the [other] to [actual]
     *
     * @param actual the [StatBase] to add to
     * @param other the [StatBase] to add
     * @return the [StatBase] being validated
     */
    fun StatBase.shouldBeSumOf(actual: StatBase, other: StatBase): StatBase = asClue {
        hull.shouldBeSumOf(actual.hull, other.hull)
        shield.shouldBeSumOf(actual.shield, other.shield)
        energy.shouldBeSumOf(actual.energy, other.energy)
        attack shouldBe actual.attack + other.attack
        defense shouldBe actual.defense + other.defense
        speed shouldBe actual.speed + other.speed
        aggro shouldBe actual.aggro + other.aggro
        this
    }

    /**
     * ## [shouldBeSumOf]
     * check if the [StatMultiplier] is the result of adding the [other] to [actual]
     *
     * @param actual the [StatMultiplier] to add to
     * @param other the [StatMultiplier] to add
     * @return the [StatMultiplier] being validated
     */
    fun StatMultiplier.shouldBeSumOf(actual: StatMultiplier, other: StatMultiplier): StatMultiplier = asClue {
        critical shouldBe actual.critical + other.critical
        this
    }

    /**
     * ## [shouldBeSumOf]
     * check if the [StatRate] is the result of adding the [other] to [actual]
     *
     * @param actual the [StatRate] to add to
     * @param other the [StatRate] to add
     * @return the [StatRate] being validated
     */
    fun StatRate.shouldBeSumOf(actual: StatRate, other: StatRate): StatRate = asClue {
        shieldRegeneration shouldBe actual.shieldRegeneration + other.shieldRegeneration
        critical shouldBe actual.critical + other.critical
        this
    }

    /**
     * ## [shouldBeSumOf]
     * check if the [StatProgression] is the result of adding the [other] to [actual]
     *
     * @param actual the [StatProgression] to add to
     * @param other the [StatProgression] to add
     * @return the [StatProgression] being validated
     */
    fun StatProgression.shouldBeSumOf(actual: StatProgression, other: StatProgression): StatProgression = asClue {
        level shouldBe actual.level
        experience shouldBe actual.experience + other.experience
        this
    }

    /**
     * ## [shouldBeSumOf]
     * check if the [CurrentMaxDouble] is the result of adding the [other] to [actual]
     *
     * @param actual the [CurrentMaxDouble] to add to
     * @param other the [CurrentMaxDouble] to add
     * @return the [CurrentMaxDouble] being validated
     */
    fun CurrentMaxDouble.shouldBeSumOf(actual: CurrentMaxDouble, other: CurrentMaxDouble): CurrentMaxDouble = asClue {
        max shouldBe actual.max + other.max
        current shouldBe actual.current
        this
    }

    /**
     * ## [shouldBeSubtractedOf]
     * check if the [Stat] is the result of subtracting the [other] from [actual]
     *
     * @param actual the [Stat] to subtract from
     * @param other the [Stat] to subtract
     * @return the [Stat] being validated
     */
    fun Stat.shouldBeSubtractedOf(actual: Stat, other: Stat): Stat = asClue {
        base.shouldBeSubtractedOf(actual.base, other.base)
        rate.shouldBeSubtractedOf(actual.rate, other.rate)
        multiplier.shouldBeSubtractedOf(actual.multiplier, other.multiplier)
        progression shouldBe actual.progression
        this
    }

    /**
     * ## [shouldBeSubtractedOf]
     * check if the [StatBase] is the result of subtracting the [other] from [actual]
     *
     * @param actual the [StatBase] to subtract from
     * @param other the [StatBase] to subtract
     * @return the [StatBase] being validated
     */
    fun StatBase.shouldBeSubtractedOf(actual: StatBase, other: StatBase): StatBase = asClue {
        hull.shouldBeSubtractedOf(actual.hull, other.hull)
        shield.shouldBeSubtractedOf(actual.shield, other.shield)
        energy.shouldBeSubtractedOf(actual.energy, other.energy)
        attack shouldBe actual.attack - other.attack
        defense shouldBe actual.defense - other.defense
        speed shouldBe actual.speed - other.speed
        aggro shouldBe actual.aggro - other.aggro
        this
    }

    /**
     * ## [shouldBeSubtractedOf]
     * check if the [StatMultiplier] is the result of subtracting the [other] from [actual]
     *
     * @param actual the [StatMultiplier] to subtract from
     * @param other the [StatMultiplier] to subtract
     * @return the [StatMultiplier] being validated
     */
    fun StatMultiplier.shouldBeSubtractedOf(actual: StatMultiplier, other: StatMultiplier): StatMultiplier = asClue {
        critical shouldBe actual.critical - other.critical
        this
    }

    /**
     * ## [shouldBeSubtractedOf]
     * check if the [StatMultiplier] is the result of subtracting the [other] from [actual]
     *
     * @param actual the [StatMultiplier] to subtract from
     * @param other the [StatMultiplier] to subtract
     * @return the [StatMultiplier] being validated
     */
    fun StatRate.shouldBeSubtractedOf(actual: StatRate, other: StatRate): StatRate = asClue {
        shieldRegeneration shouldBe actual.shieldRegeneration - other.shieldRegeneration
        critical shouldBe actual.critical - other.critical
        this
    }

    /**
     * ## [shouldBeSubtractedOf]
     * check if the [CurrentMaxDouble] is the result of subtracting the [other] from [actual]
     *
     * @param actual the [CurrentMaxDouble] to subtract from
     * @param other the [CurrentMaxDouble] to subtract
     * @return the [CurrentMaxDouble] being validated
     */
    fun CurrentMaxDouble.shouldBeSubtractedOf(actual: CurrentMaxDouble, other: CurrentMaxDouble): CurrentMaxDouble = asClue {
        max shouldBe if (actual.max - other.max > ZERO) actual.max - other.max else ZERO
        max shouldNotBeLessThan ZERO
        current shouldNotBeGreaterThan max
        this
    }
}

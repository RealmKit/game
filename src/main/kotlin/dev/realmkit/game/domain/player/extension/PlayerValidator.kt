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

package dev.realmkit.game.domain.player.extension

import dev.realmkit.game.core.exception.ValidationException
import dev.realmkit.game.core.extension.ValidationExtensions.notBlank
import dev.realmkit.game.domain.player.document.Player
import dev.realmkit.game.domain.resource.extension.ResourceValidator
import dev.realmkit.game.domain.ship.validator.ShipValidator
import io.konform.validation.Invalid
import io.konform.validation.Valid
import io.konform.validation.Validation
import io.konform.validation.ValidationResult

/**
 * # [PlayerValidator]
 * [Player] validations
 */
object PlayerValidator {
    /**
     * ## [validation]
     * [Player] [Validation] object
     */
    val validation: Validation<Player> = Validation {
        Player::name required { notBlank() }
        Player::ship required { run(ShipValidator.validation) }
        Player::resource required { run(ResourceValidator.validation) }
    }

    /**
     * ## [validated]
     * Validates the [Player]
     *
     * @param block the resulting [Player] on its [ValidationResult] context
     * @return the [block] result
     * @throws ValidationException if [Player] has validation issues
     */
    @Throws(ValidationException::class)
    infix fun <R> Player.validated(block: ValidationResult<Player>.(Player) -> R): R =
        validation(this).let { validation ->
            when (validation) {
                is Valid -> validation.block(this)
                is Invalid -> throw ValidationException(validation)
            }
        }
}

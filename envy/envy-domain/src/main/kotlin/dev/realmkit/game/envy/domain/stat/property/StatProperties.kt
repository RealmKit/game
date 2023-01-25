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

package dev.realmkit.game.envy.domain.stat.property

import dev.realmkit.game.envy.domain.stat.property.StatProperties.InitialStatusProperties.initialStatus
import jakarta.annotation.PostConstruct
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("application.initial.stats")
data class StatProperties(
    val level: Long = 0,
    val experience: Long = 0,
    val health: Long = 0,
    val mana: Long = 0,
    val stamina: Long = 0,
    val attack: Long = 0,
    val speed: Long = 0,
    val experienceMultiplier: Double = 0.0,
    val dropMultiplier: Double = 0.0,
    val criticalMultiplier: Double = 0.0,
    val criticalChance: Double = 0.0,
    val evadeChance: Double = 0.0,
) {
    object InitialStatusProperties {
        var initialStatus: StatProperties = StatProperties()
    }

    @PostConstruct
    fun initialize() {
        initialStatus = this
    }
}

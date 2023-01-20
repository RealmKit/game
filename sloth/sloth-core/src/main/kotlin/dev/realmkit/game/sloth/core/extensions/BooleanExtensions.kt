package dev.realmkit.game.sloth.core.extensions

infix fun <T> Boolean?.ifTrue(block: () -> T): T? =
    this.takeIf { this == true }?.let { block() }

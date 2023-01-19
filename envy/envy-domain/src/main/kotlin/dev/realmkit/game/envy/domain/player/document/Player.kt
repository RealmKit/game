package dev.realmkit.game.envy.domain.player.document

import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Player(
    val name: String,
//    val stat: Stat = Stat(),
//    val currency: Currency = Currency(),
//    val equipmentSlot: EquipmentSlot = EquipmentSlot(),
) /*: BaseDocument()*/ {
    companion object
}
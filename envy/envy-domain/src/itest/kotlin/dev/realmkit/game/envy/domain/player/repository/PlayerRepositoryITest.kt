package dev.realmkit.game.envy.domain.player.repository

import dev.realmkit.test.sloth.testutils.infra.ITestContext
import dev.realmkit.test.sloth.testutils.specs.ITestSpec
import io.kotest.matchers.longs.shouldBeZero
import io.kotest.matchers.nulls.shouldNotBeNull

@ITestContext
class PlayerRepositoryITest(
    private val playerRepository: PlayerRepository,
) : ITestSpec({
    expect("all beans to be inject") {
        playerRepository.shouldNotBeNull()
    }

    expect("it should be empty") {
        context {
            playerRepository.count().shouldBeZero()
        }
    }

    expect("it should create a new Player") {
        context {
            playerRepository.count().shouldBeZero()
        }

//        check(Player.arbitrary) { player ->
//            playerRepository.save(player).shouldNotBeNull()
//            playerRepository.findById(player.id).asClue { find ->
//                find.shouldBePresent()
//                    .asClue {
//                        it.id shouldBe player.id
//                        it.createdDate.shouldNotBeNull()
//                        it.updatedDate.shouldNotBeNull()
//                        it.name shouldBe player.name
//                        it.stat.shouldBePositive()
//                        it.currency.gold.shouldNotBeNull()
//                        it.currency.gem.shouldNotBeNull()
//                        it.equipmentSlot.weapon.shouldNotBeNull()
//                            .stat.shouldBePositive()
//                        it.equipmentSlot.armor.shouldNotBeNull()
//                            .stat.shouldBePositive()
//                        it.equipmentSlot.ring.shouldNotBeNull()
//                            .stat.shouldBePositive()
//                    }
//            }
//        }
    }
})

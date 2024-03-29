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

package dev.realmkit.game.domain.item.repository

import dev.realmkit.game.domain.item.document.Item
import dev.realmkit.hellper.extension.FakerExtensions
import dev.realmkit.hellper.fixture.item.ItemFixture.fixture
import dev.realmkit.hellper.infra.IntegrationTestContext
import dev.realmkit.hellper.spec.IntegrationTestSpec
import io.kotest.assertions.asClue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.optional.shouldBePresent
import io.kotest.matchers.string.shouldNotBeBlank
import io.kotest.property.checkAll

@IntegrationTestContext
class ItemRepositoryTest(
    private val itemRepository: ItemRepository,
) : IntegrationTestSpec({
    expect("all beans to be inject") {
        itemRepository.shouldNotBeNull()
    }

    expect("it should create Item") {
        checkAll(Item.fixture) { item ->
            item.owner = FakerExtensions.faker.random.nextUUID()
            itemRepository.run {
                save(item).shouldNotBeNull()
                findById(item.id).shouldBePresent().asClue { find ->
                    find.id.shouldNotBeNull()
                    find.createdAt.shouldNotBeNull()
                    find.updatedAt.shouldNotBeNull()
                    find.version.shouldNotBeNull()
                    find.name.shouldNotBeBlank()
                    find.name.shouldNotBeBlank()
                    find.stat.shouldNotBeNull()
                }
            }
        }
    }
})

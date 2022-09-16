package demo.realm.data

import demo.realm.data.fixtures.ResetAppRealm
import demo.realm.data.fixtures.toListIn
import demo.realm.data.fixtures.use
import demo.realm.data.model.Credentials
import demo.realm.data.model.Item
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

internal class UserRealmImplTest : ResetAppRealm {

    @Test
    fun `items should update for credentials with +=`() {
        runBlocking {
            val value = mockk<Item>(relaxed = true)
            val credentials = mockk<Credentials>()
            val items = mutableListOf<List<Item>>()

            UserRealmImpl.items.toListIn(this, items).use {
                UserRealmImpl += mockk()

                AppRealmImpl += credentials
                UserRealmImpl += value

                AppRealmImpl += mockk()
                AppRealmImpl += credentials
                AppRealmImpl -= null

                assertEquals(
                    listOf(
                        emptyList(),
                        listOf(value),
                        emptyList(),
                        listOf(value),
                        emptyList(),
                    ),
                    items,
                )
            }
        }
    }

    @Test
    fun `+= should add item`() {
        runBlocking {
            val value = mockk<Item>(relaxed = true)
            AppRealmImpl += mockk()
            val items = mutableListOf<List<Item>>()

            UserRealmImpl.items.toListIn(this, items).use {
                UserRealmImpl += value

                assertEquals(
                    listOf(
                        emptyList(), listOf(value)
                    ),
                    items,
                )
            }
        }
    }

    @Test
    fun `-= should remove item`() {
        runBlocking {
            val value = mockk<Item>(relaxed = true)
            AppRealmImpl += mockk()
            UserRealmImpl += value
            val items = mutableListOf<List<Item>>()

            UserRealmImpl.items.toListIn(this, items).use {
                UserRealmImpl -= value

                assertEquals(
                    listOf(
                        listOf(value),
                        emptyList(),
                    ),
                    items,
                )
            }
        }
    }
}

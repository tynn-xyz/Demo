package demo.realm.data

import demo.realm.data.fixtures.ResetAppRealm
import demo.realm.data.fixtures.toListAsync
import demo.realm.data.model.Credentials
import demo.realm.data.model.Item
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
internal class UserRealmImplTest : ResetAppRealm {

    @Test
    fun `items should update for credentials with +=`() {
        runBlockingTest {
            val value = mockk<Item>(relaxed = true)
            val credentials = mockk<Credentials>()
            val items = mutableListOf<List<Item>>()
            val job = UserRealmImpl.items.toListAsync(
                this,
                items,
            )

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

            job.cancel()
        }
    }

    @Test
    fun `+= should add item`() {
        runBlockingTest {
            val value = mockk<Item>(relaxed = true)
            AppRealmImpl += mockk()
            val items = mutableListOf<List<Item>>()
            val job = UserRealmImpl.items.toListAsync(
                this,
                items,
            )

            UserRealmImpl += value

            assertEquals(
                listOf(
                    emptyList(),
                    listOf(value)
                ),
                items,
            )

            job.cancel()
        }
    }

    @Test
    fun `-= should remove item`() {
        runBlockingTest {
            val value = mockk<Item>(relaxed = true)
            AppRealmImpl += mockk()
            UserRealmImpl += value
            val items = mutableListOf<List<Item>>()
            val job = UserRealmImpl.items.toListAsync(
                this,
                items,
            )

            UserRealmImpl -= value

            assertEquals(
                listOf(
                    listOf(value),
                    emptyList(),
                ),
                items,
            )

            job.cancel()
        }
    }
}

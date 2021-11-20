package demo.realm.data

import demo.realm.data.fixtures.ResetAppRealm
import demo.realm.data.fixtures.toListAsync
import demo.realm.data.model.Credentials
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
internal class AppRealmImplTest : ResetAppRealm {

    @Test
    fun `credentials should update with += and -=`() {
        runBlockingTest {
            val value = mockk<Credentials>()
            val credentials = mutableListOf<Credentials?>()
            val job = AppRealmImpl.credentials.toListAsync(
                this,
                credentials,
            )

            AppRealmImpl += value
            AppRealmImpl -= null

            assertEquals(
                listOf(
                    null,
                    value,
                    null,
                ),
                credentials,
            )

            job.cancel()
        }
    }

    @Test
    fun `isLoggedIn should update with += and -=`() {
        runBlockingTest {
            val isLoggedIn = mutableListOf<Boolean>()
            val job = AppRealmImpl.isLoggedIn.toListAsync(
                this,
                isLoggedIn,
            )

            AppRealmImpl += mockk()
            AppRealmImpl += mockk()
            AppRealmImpl -= null

            assertEquals(
                listOf(
                    false,
                    true,
                    false,
                ),
                isLoggedIn,
            )

            job.cancel()
        }
    }

    @Test
    fun `errors should not update with += and -=`() {
        runBlockingTest {
            val errors = mutableListOf<Throwable>()
            val job = AppRealmImpl.errors.toListAsync(
                this,
                errors,
            )

            AppRealmImpl += mockk()
            AppRealmImpl -= null

            assertEquals(
                listOf(),
                errors,
            )

            job.cancel()
        }
    }
}

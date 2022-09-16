package demo.realm.data

import demo.realm.data.fixtures.ResetAppRealm
import demo.realm.data.fixtures.toListIn
import demo.realm.data.fixtures.use
import demo.realm.data.model.Credentials
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

internal class AppRealmImplTest : ResetAppRealm {

    @Test
    fun `credentials should update with += and -=`() {
        runBlocking {
            val value = mockk<Credentials>()
            val credentials = mutableListOf<Credentials?>()

            AppRealmImpl.credentials.toListIn(this, credentials).use {
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
            }
        }
    }

    @Test
    fun `isLoggedIn should update with += and -=`() {
        runBlocking {
            val isLoggedIn = mutableListOf<Boolean>()

            AppRealmImpl.isLoggedIn.toListIn(this, isLoggedIn).use {
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
            }
        }
    }

    @Test
    fun `errors should not update with += and -=`() {
        runBlocking {
            val errors = mutableListOf<Throwable>()

            AppRealmImpl.errors.toListIn(this, errors).use {
                AppRealmImpl += mockk()
                AppRealmImpl -= null

                assertEquals(
                    listOf(),
                    errors,
                )
            }
        }
    }
}

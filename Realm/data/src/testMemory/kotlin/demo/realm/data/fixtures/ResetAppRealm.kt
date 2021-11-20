package demo.realm.data.fixtures

import demo.realm.data.AppRealm
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest

internal interface ResetAppRealm {

    @BeforeTest
    fun removeCredentials() {
        runBlocking {
            AppRealm -= null
        }
    }
}

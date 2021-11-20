package demo.realm.app.fixtures

import demo.realm.data.AppRealm
import demo.realm.data.UserRealm
import io.mockk.mockkObject
import kotlin.test.BeforeTest

interface MockRealm : UnMockAll {

    @BeforeTest
    fun mockkRealm() = mockkObject(AppRealm, UserRealm)
}

package demo.realm.app.fixtures

import io.mockk.unmockkAll
import kotlin.test.AfterTest

interface UnMockAll {

    @AfterTest
    fun unmockAll() = unmockkAll()
}

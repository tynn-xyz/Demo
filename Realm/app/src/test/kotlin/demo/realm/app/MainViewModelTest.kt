package demo.realm.app

import demo.realm.app.fixtures.MockRealm
import demo.realm.app.fixtures.UnconfinedMain
import demo.realm.data.AppRealm
import demo.realm.data.UserRealm
import demo.realm.data.model.Credentials
import demo.realm.data.model.Item
import io.mockk.coVerifyAll
import io.mockk.mockk
import kotlin.test.Test

internal class MainViewModelTest : UnconfinedMain, MockRealm {

    private val viewModel = MainViewModel()

    @Test
    fun `storeItem should delegate to UserRealm`() {
        val item = mockk<Item>()

        viewModel.storeItem(item)

        coVerifyAll { UserRealm += item }
    }

    @Test
    fun `deleteItem should delegate to UserRealm`() {
        val item = mockk<Item>()

        viewModel.deleteItem(item)

        coVerifyAll { UserRealm -= item }
    }

    @Test
    fun `login should delegate to AppRealm`() {
        val credentials = mockk<Credentials>()

        viewModel.login(credentials)

        coVerifyAll { AppRealm += credentials }
    }

    @Test
    fun `logout should delegate to AppRealm`() {
        viewModel.logout()

        coVerifyAll { AppRealm -= null }
    }
}

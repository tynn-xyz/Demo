package demo.realm.app.login

import androidx.lifecycle.SavedStateHandle
import demo.realm.data.model.Credentials
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class LoginViewModelTest {

    private val state = SavedStateHandle()
    private val viewModel = LoginViewModel(state)

    @Test
    fun `username should delegate to state`() {
        assertNull(viewModel.username)

        state.set("username", "username")
        assertEquals(
            "username",
            viewModel.username,
        )

        viewModel.username = "username2"
        assertEquals(
            "username2",
            state["username"],
        )
    }

    @Test
    fun `password should delegate to state`() {
        assertNull(viewModel.password)

        state.set("password", "password")
        assertEquals(
            "password",
            viewModel.password,
        )

        viewModel.password = "password2"
        assertEquals(
            "password2",
            state["password"],
        )
    }

    @Test
    fun `getCredentials should create new Credentials with username and password`() {
        val username = "username"
        val password = "password"

        viewModel.username = username
        viewModel.password = password

        assertEquals(
            Credentials(
                username = username,
                password = password
            ),
            viewModel.getCredentials(),
        )
    }

    @Test
    fun `getCredentials should not create new Credentials without username`() {
        val username = "  "
        val password = "password"

        viewModel.username = username
        viewModel.password = password

        assertNull(viewModel.getCredentials())
    }

    @Test
    fun `getCredentials should not create new Credentials without password`() {
        val username = "username"
        val password = "  "

        viewModel.username = username
        viewModel.password = password

        assertNull(viewModel.getCredentials())
    }

    @Test
    fun `getCredentials should not create new Credentials without username orpassword`() {
        val username = "  "
        val password = "  "

        viewModel.username = username
        viewModel.password = password

        assertNull(viewModel.getCredentials())
    }
}

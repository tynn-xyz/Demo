package demo.realm.app.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import demo.realm.data.model.Credentials
import xyz.tynn.hoppa.delegate.getValue
import xyz.tynn.hoppa.delegate.setValue

class LoginViewModel(
    state: SavedStateHandle,
) : ViewModel() {

    var username: String? by state
    var password: String? by state

    fun getCredentials(): Credentials? {
        return Credentials(
            username.takeUnless {
                it.isNullOrBlank()
            } ?: return null,
            password.takeUnless {
                it.isNullOrBlank()
            } ?: return null,
        )
    }
}

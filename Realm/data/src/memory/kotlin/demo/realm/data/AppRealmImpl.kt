package demo.realm.data

import demo.realm.data.model.Credentials
import kotlinx.coroutines.flow.*

internal object AppRealmImpl : AppRealm {

    internal val credentials = MutableStateFlow<Credentials?>(null)

    override val isLoggedIn = credentials.map {
        it != null
    }.distinctUntilChanged()
    override val errors = MutableSharedFlow<Throwable>()

    override suspend operator fun plusAssign(credentials: Credentials) {
        this.credentials.update { credentials }
    }

    override suspend operator fun minusAssign(nothing: Nothing?) {
        credentials.update { null }
    }
}

package demo.realm.data

import demo.realm.data.model.Credentials
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface AppRealm {

    val isLoggedIn: Flow<Boolean>
    val errors: SharedFlow<Throwable>

    suspend operator fun plusAssign(credentials: Credentials)
    suspend operator fun minusAssign(nothing: Nothing?)

    companion object : AppRealm by AppRealmImpl
}

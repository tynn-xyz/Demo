@file:[JvmMultifileClass JvmName("RealmKt")]

package demo.realm.data.ktx

import io.realm.mongodb.App
import io.realm.mongodb.AuthenticationListener
import io.realm.mongodb.Credentials
import io.realm.mongodb.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged

internal fun App.currentUserFlow() = callbackFlow {
    val listener = object : AuthenticationListener {
        override fun loggedIn(user: User) = emit()
        override fun loggedOut(user: User) = emit()

        fun emit() {
            trySend(currentUser())
        }
    }
    addAuthenticationListener(listener)
    listener.emit()
    awaitClose { removeAuthenticationListener(listener) }
}.conflate().distinctUntilChanged()

internal suspend fun App.loginAwait(
    credentials: Credentials,
) = suspendRealmAsyncTask<User> {
    loginAsync(credentials, it)
}

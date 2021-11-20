@file:[JvmMultifileClass JvmName("RealmKt")]

package demo.realm.data.ktx

import io.realm.mongodb.User

internal suspend fun User.logoutAwait() {
    suspendRealmAsyncTask<User> {
        logOutAsync(it)
    }
}

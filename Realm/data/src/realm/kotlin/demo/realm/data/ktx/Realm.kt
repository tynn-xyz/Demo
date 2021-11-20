@file:[JvmMultifileClass JvmName("RealmKt")]

package demo.realm.data.ktx

import io.realm.Realm
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal suspend inline fun User.getRealmInstance(
    partitionValue: String?,
    builder: SyncConfiguration.Builder.() -> Unit,
) = getRealmInstance(
    SyncConfiguration.Builder(
        this,
        partitionValue,
    ).apply(builder).build()
)

internal suspend fun getRealmInstance(
    config: SyncConfiguration,
) = suspendCancellableCoroutine<Realm> {
    with(Realm.getInstanceAsync(config, it.asCallback())) {
        it.invokeOnCancellation { cancel() }
    }
}

private fun Continuation<Realm>.asCallback() = object : Realm.Callback() {
    override fun onError(e: Throwable) = resumeWithException(e)
    override fun onSuccess(realm: Realm) = resume(realm)
}

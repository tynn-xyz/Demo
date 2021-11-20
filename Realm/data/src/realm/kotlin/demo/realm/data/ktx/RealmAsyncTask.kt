@file:[JvmMultifileClass JvmName("RealmKt")]

package demo.realm.data.ktx

import io.realm.RealmAsyncTask
import io.realm.mongodb.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal suspend inline fun <T> suspendRealmAsyncTask(
    crossinline asyncTask: (App.Callback<T>) -> RealmAsyncTask,
) = withContext(Dispatchers.Main.immediate) {
    suspendCancellableCoroutine<T> {
        with(asyncTask(it.asAppCallback())) {
            it.invokeOnCancellation {
                cancel()
            }
        }
    }
}

private fun <T> Continuation<T>.asAppCallback() = App.Callback<T> {
    if (it.isSuccess) resume(it.get())
    else resumeWithException(it.error)
}

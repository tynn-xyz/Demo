@file:[JvmMultifileClass JvmName("UtilsKt")]

package demo.realm.data.fixtures

import kotlinx.coroutines.Job

internal inline fun <R> Job.use(block: () -> R) = try {
    block()
} finally {
    cancel()
}

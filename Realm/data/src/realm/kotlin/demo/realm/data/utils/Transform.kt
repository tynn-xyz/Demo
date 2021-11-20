@file:[JvmMultifileClass JvmName("UtilsKt")]

package demo.realm.data.utils

import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transformLatest
import java.io.Closeable

internal fun <T, R : Closeable?> Flow<T>.useMapLatest(
    transform: suspend (T) -> R,
) = transformLatest { value ->
    transform(value).also {
        emit(it)
    }?.use {
        awaitCancellation()
    }
}

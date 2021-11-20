@file:[JvmMultifileClass JvmName("UtilsKt")]

package demo.realm.data.fixtures

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList

internal fun <T> Flow<T>.toListAsync(
    scope: CoroutineScope,
    destination: MutableList<T>,
) = scope.async { toList(destination) }

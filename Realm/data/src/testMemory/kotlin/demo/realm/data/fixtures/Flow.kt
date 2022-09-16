@file:[JvmMultifileClass JvmName("UtilsKt")]

package demo.realm.data.fixtures

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

internal fun <T> Flow<T>.toListIn(
    scope: CoroutineScope,
    destination: MutableList<T>,
) = scope.launch(Unconfined) { toList(destination) }

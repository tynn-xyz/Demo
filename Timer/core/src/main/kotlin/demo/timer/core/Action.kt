package demo.timer.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

typealias Action = @JvmSuppressWildcards(false) suspend () -> Unit

fun Action.launchIn(
    scope: CoroutineScope,
) = scope.launch {
    this@launchIn()
}

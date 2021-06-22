package demo.timer.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

//interface Action : suspend () -> Unit

interface Action {
    suspend operator fun invoke()
}

fun Action.launchIn(
    scope: CoroutineScope,
) = scope.launch {
    this@launchIn()
}

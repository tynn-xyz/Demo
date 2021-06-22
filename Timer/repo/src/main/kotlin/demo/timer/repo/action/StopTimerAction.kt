package demo.timer.repo.action

import demo.timer.core.Action
import demo.timer.repo.store.TimerStore
import javax.inject.Inject

internal class StopTimerAction @Inject constructor(
    private val store: TimerStore,
) : Action {

    override suspend fun invoke() = store.stopTimer()
}

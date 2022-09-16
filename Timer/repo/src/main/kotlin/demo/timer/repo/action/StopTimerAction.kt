package demo.timer.repo.action

import demo.timer.core.Action
import demo.timer.repo.store.TimerStore
import javax.inject.Inject

internal class StopTimerAction @Inject constructor(
    store: TimerStore,
) : Action by store::stopTimer

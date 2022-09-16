package demo.timer.repo.action

import demo.timer.core.Action
import demo.timer.repo.store.TimerStore
import javax.inject.Inject

internal class StartTimerAction @Inject constructor(
    store: TimerStore,
) : Action by store::startTimer

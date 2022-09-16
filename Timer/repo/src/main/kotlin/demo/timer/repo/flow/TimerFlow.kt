package demo.timer.repo.flow

import demo.timer.core.Timer
import demo.timer.repo.store.TimerStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class TimerFlow @Inject constructor(
    store: TimerStore,
) : Flow<Timer> by store.flow

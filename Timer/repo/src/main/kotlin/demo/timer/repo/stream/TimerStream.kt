package demo.timer.repo.stream

import demo.timer.core.Stream
import demo.timer.core.Timer
import demo.timer.repo.store.TimerStore
import javax.inject.Inject

internal class TimerStream @Inject constructor(
    store: TimerStore,
) : Stream<Timer> by store.flow

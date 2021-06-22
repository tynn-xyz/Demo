package demo.timer.repo.store

import demo.timer.core.Property
import demo.timer.core.Timer
import demo.timer.core.Timer.*
import demo.timer.core.Timer.Factory.paused
import demo.timer.core.Timer.Factory.started
import demo.timer.core.Timer.Factory.stopped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.time.Clock
import java.time.Duration.ZERO
import java.time.Duration.between
import java.time.Instant.now
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class TimerStore @Inject constructor(
    store: Property<Timer>,
    private val clock: Clock,
) {

    val flow: Flow<Timer> get() = timerFlow

    private var timerStore by store
    private val timerFlow = MutableStateFlow(timerStore)
    private val timerMutex = Mutex()

    suspend fun pauseTimer() {
        updateTimer(
            fromStarted = {
                paused(
                    startedAt = it.startedAt,
                    pausedAt = now(clock),
                    pausedFor = it.pausedFor,
                )
            },
        )
    }

    suspend fun startTimer() {
        updateTimer(
            fromPaused = {
                started(
                    startedAt = it.startedAt,
                    pausedFor = between(it.pausedAt, now(clock)) + it.pausedFor
                )
            },
            fromStopped = {
                started(
                    startedAt = now(clock),
                    pausedFor = ZERO
                )
            },
        )
    }

    suspend fun stopTimer() {
        updateTimer(
            fromPaused = {
                stopped()
            },
            fromStarted = {
                stopped()
            },
        )
    }

    private suspend inline fun updateTimer(
        fromPaused: (Paused) -> Timer = { it },
        fromStarted: (Started) -> Timer = { it },
        fromStopped: (Stopped) -> Timer = { it },
    ) = timerMutex.withLock {
        val value = when (val value = timerFlow.value) {
            is Paused -> fromPaused(value)
            is Started -> fromStarted(value)
            is Stopped -> fromStopped(value)
        }
        timerStore = value
        timerFlow.value = value
    }
}

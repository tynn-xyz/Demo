package demo.timer.core

import java.time.Duration
import java.time.Instant

sealed interface Timer {

    sealed interface Paused : Timer {
        val startedAt: Instant
        val pausedAt: Instant
        val pausedFor: Duration
    }

    sealed interface Started : Timer {
        val startedAt: Instant
        val pausedFor: Duration
    }

    sealed interface Stopped : Timer

    override fun equals(
        other: Any?,
    ): Boolean

    companion object Factory {

        fun paused(
            startedAt: Instant,
            pausedAt: Instant,
            pausedFor: Duration,
        ): Paused = Paused(
            startedAt = startedAt,
            pausedAt = pausedAt,
            pausedFor = pausedFor,
        )

        fun started(
            startedAt: Instant,
            pausedFor: Duration,
        ): Started = Started(
            startedAt = startedAt,
            pausedFor = pausedFor,
        )

        fun stopped(): Stopped = Stopped
    }
}

private data class Paused(
    override val startedAt: Instant,
    override val pausedAt: Instant,
    override val pausedFor: Duration,
) : Timer.Paused

private data class Started(
    override val startedAt: Instant,
    override val pausedFor: Duration,
) : Timer.Started

private object Stopped : Timer.Stopped {

    override fun equals(
        other: Any?,
    ) = other is Stopped

    override fun toString() = "Stopped"
}

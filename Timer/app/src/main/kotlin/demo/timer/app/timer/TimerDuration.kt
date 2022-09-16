package demo.timer.app.timer

import android.content.Context
import demo.timer.app.R.string.*
import demo.timer.core.Timer
import kotlinx.parcelize.Parcelize
import xyz.tynn.astring.AString
import java.time.Clock
import java.time.Duration
import java.time.Duration.ZERO
import java.time.Duration.between
import java.time.Instant.now
import javax.inject.Inject

@Parcelize
data class TimerDuration internal constructor(
    val duration: Duration,
    val isStarted: Boolean,
) : AString {

    override fun invoke(
        context: Context,
    ) = with(duration) {
        context.getString(
            when {
                seconds < 60 -> format_seconds
                seconds < 60 * 60 -> format_minutes
                seconds < 24 * 60 * 60 -> format_hours
                else -> format_days
            },
            seconds % 60,
            seconds / 60 % 60,
            seconds / 60 / 60 % 24,
            seconds / 60 / 60 / 24,
        )
    }

    class Factory @Inject constructor(
        private val clock: Clock,
    ) {

        operator fun invoke(
            timer: Timer.Paused,
        ) = TimerDuration(
            between(timer.startedAt, timer.pausedAt) - timer.pausedFor,
            false,
        )

        operator fun invoke(
            timer: Timer.Started,
        ) = TimerDuration(
            between(timer.startedAt, now(clock)) - timer.pausedFor,
            true,
        )

        @Suppress("UNUSED_PARAMETER")
        operator fun invoke(
            timer: Timer.Stopped,
        ) = TimerDuration(
            ZERO,
            false,
        )
    }
}

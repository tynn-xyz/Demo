package demo.timer.app.timer

import android.content.Context
import demo.timer.app.R.string.*
import io.mockk.mockk
import io.mockk.verifyAll
import java.time.Duration.ofDays
import java.time.Duration.ofSeconds
import kotlin.test.Test

internal class TimerDurationTest {

    val context = mockk<Context>(relaxed = true)

    @Test
    fun `invoke should format seconds`() {
        TimerDuration(
            ofSeconds(59),
            false,
        ).invoke(context)

        verifyAll {
            context.getString(
                format_seconds,
                59L, 0L, 0L, 0L,
            )
        }
    }

    @Test
    fun `invoke should format minutes`() {
        TimerDuration(
            ofSeconds(5).plusMinutes(2),
            false,
        ).invoke(context)

        verifyAll {
            context.getString(
                format_minutes,
                5L, 2L, 0L, 0L,
            )
        }
    }

    @Test
    fun `invoke should format hours`() {
        TimerDuration(
            ofSeconds(43).plusMinutes(1).plusHours(4),
            false,
        ).invoke(context)

        verifyAll {
            context.getString(
                format_hours,
                43L, 1L, 4L, 0L,
            )
        }
    }

    @Test
    fun `invoke should format days`() {
        TimerDuration(
            ofSeconds(1).plusDays(1),
            false,
        ).invoke(context)

        verifyAll {
            context.getString(
                format_days,
                1L, 0L, 0L, 1L,
            )
        }
    }

    @Test
    fun `invoke should not format years`() {
        TimerDuration(
            ofDays(421),
            false,
        ).invoke(context)

        verifyAll {
            context.getString(
                format_days,
                0L, 0L, 0L, 421L,
            )
        }
    }
}

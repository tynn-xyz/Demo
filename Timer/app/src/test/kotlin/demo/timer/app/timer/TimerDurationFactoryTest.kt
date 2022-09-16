package demo.timer.app.timer

import demo.timer.core.Timer.Factory.paused
import demo.timer.core.Timer.Factory.started
import demo.timer.core.Timer.Factory.stopped
import java.time.Clock.fixed
import java.time.Duration.*
import java.time.Instant.now
import java.time.ZoneId.systemDefault
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TimerDurationFactoryTest {

    private val clock = fixed(now() + ofHours(1), systemDefault())
    private val factory = TimerDuration.Factory(clock)

    private val paused = paused(now() - ofMinutes(2), now(), ofMinutes(1))
    private val started = started(now(), ofMinutes(3))
    private val stopped = stopped()

    @Test
    fun `invoke should create duration from paused`() {
        assertEquals(
            TimerDuration(
                between(
                    paused.startedAt + paused.pausedFor,
                    paused.pausedAt,
                ),
                false,
            ),
            factory.invoke(paused),
        )
    }

    @Test
    fun `invoke should create duration from started`() {
        assertEquals(
            TimerDuration(
                between(
                    started.startedAt + started.pausedFor,
                    now(clock),
                ),
                true,
            ),
            factory.invoke(started),
        )
    }

    @Test
    fun `invoke should create duration from stopped`() {
        assertEquals(
            TimerDuration(
                ZERO,
                false,
            ),
            factory.invoke(stopped),
        )
    }
}

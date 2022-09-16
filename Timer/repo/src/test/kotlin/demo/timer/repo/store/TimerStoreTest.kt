package demo.timer.repo.store

import demo.timer.core.Property
import demo.timer.core.Timer
import demo.timer.core.Timer.Factory.paused
import demo.timer.core.Timer.Factory.started
import demo.timer.core.Timer.Factory.stopped
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.time.Clock.fixed
import java.time.Duration.*
import java.time.Instant.now
import java.time.ZoneId.systemDefault
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TimerStoreTest {

    private val clock = fixed(now() + ofHours(1), systemDefault())
    private val property = mockk<Property<Timer>>(relaxed = true)

    private val state by lazy { TimerStore(property, clock) }

    private val paused = paused(now() - ofMinutes(1), now(), ofMinutes(1))
    private val started = started(now(), ofMinutes(2))
    private val stopped = stopped()

    @Test
    fun `flow should init with property`() {
        val timer = mockk<Timer>()
        every { property.getValue(any(), any()) } returns timer

        assertTimer(timer)
    }

    @Test
    fun `pauseTimer on paused should remain paused`() {
        every { property.getValue(any(), any()) } returns paused

        runBlocking { state.pauseTimer() }

        assertAndVerifyTimer(paused)
    }

    @Test
    fun `pauseTimer on started should become paused`() {
        every { property.getValue(any(), any()) } returns started

        runBlocking { state.pauseTimer() }

        assertAndVerifyTimer(
            paused(
                started.startedAt,
                now(clock),
                started.pausedFor,
            )
        )
    }

    @Test
    fun `pauseTimer on stopped should remain stopped`() {
        every { property.getValue(any(), any()) } returns stopped

        runBlocking { state.pauseTimer() }

        assertAndVerifyTimer(stopped)
    }

    @Test
    fun `startTimer on paused should become started`() {
        every { property.getValue(any(), any()) } returns paused

        runBlocking { state.startTimer() }

        assertAndVerifyTimer(
            started(
                paused.startedAt,
                between(paused.pausedAt, now(clock)) + paused.pausedFor,
            )
        )
    }

    @Test
    fun `startTimer on started should remain started`() {
        every { property.getValue(any(), any()) } returns started

        runBlocking { state.startTimer() }

        assertAndVerifyTimer(started)
    }

    @Test
    fun `startTimer on stopped should become started`() {
        every { property.getValue(any(), any()) } returns stopped

        runBlocking { state.startTimer() }

        assertAndVerifyTimer(started(now(clock), ZERO))
    }

    @Test
    fun `stopTimer on paused should become stopped`() {
        every { property.getValue(any(), any()) } returns paused

        runBlocking { state.stopTimer() }

        assertAndVerifyTimer(stopped)
    }

    @Test
    fun `stopTimer on started should become stopped`() {
        every { property.getValue(any(), any()) } returns started

        runBlocking { state.stopTimer() }

        assertAndVerifyTimer(stopped)
    }

    @Test
    fun `stopTimer on stopped should remain stopped`() {
        every { property.getValue(any(), any()) } returns stopped

        runBlocking { state.stopTimer() }

        assertAndVerifyTimer(stopped)
    }

    private fun assertTimer(
        expected: Timer,
    ) = assertEquals(
        expected,
        runBlocking { state.flow.first() },
    )

    private fun assertAndVerifyTimer(expected: Timer) {
        assertTimer(expected)
        verify { property.setValue(state, any(), expected) }
    }
}

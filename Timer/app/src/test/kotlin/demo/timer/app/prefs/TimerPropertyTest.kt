package demo.timer.app.prefs

import android.content.SharedPreferences
import androidx.core.content.edit
import demo.timer.core.Timer.Factory.paused
import demo.timer.core.Timer.Factory.started
import demo.timer.core.Timer.Factory.stopped
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import java.time.Duration.ofMinutes
import java.time.Instant
import java.time.Instant.now
import java.time.Instant.ofEpochMilli
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TimerPropertyTest {

    private val prefs = mockk<SharedPreferences>(relaxed = true) {
        every { contains(any()) } returns true
    }
    private var property by TimerProperty(prefs)

    private val paused = paused(nowMilli() - ofMinutes(1), nowMilli(), ofMinutes(1))
    private val started = started(nowMilli(), ofMinutes(2))
    private val stopped = stopped()

    private val startedAtKey = "timer.started.at"
    private val pausedForKey = "timer.paused.for"
    private val pausedAtKey = "timer.paused.at"

    @Test
    fun `setValue should write paused`() {
        property = paused

        verifyAll {
            prefs.edit {
                putLong(startedAtKey, paused.startedAt.toEpochMilli())
                putLong(pausedAtKey, paused.pausedAt.toEpochMilli())
                putLong(pausedForKey, paused.pausedFor.toMillis())
            }
        }
    }

    @Test
    fun `setValue should write started`() {
        property = started

        verifyAll {
            prefs.edit {
                putLong(startedAtKey, started.startedAt.toEpochMilli())
                putLong(pausedForKey, started.pausedFor.toMillis())
                remove(pausedAtKey)
            }
        }
    }

    @Test
    fun `setValue should write stopped`() {
        property = stopped

        verifyAll {
            prefs.edit {
                remove(startedAtKey)
                remove(pausedForKey)
                remove(pausedAtKey)
            }
        }
    }

    @Test
    fun `getValue should read paused`() {
        every {
            prefs.getLong(startedAtKey, 0)
        } returns paused.startedAt.toEpochMilli()
        every {
            prefs.getLong(pausedForKey, 0)
        } returns paused.pausedFor.toMillis()
        every {
            prefs.getLong(pausedAtKey, 0)
        } returns paused.pausedAt.toEpochMilli()

        assertEquals(paused, property)
    }

    @Test
    fun `getValue should read started`() {
        every {
            prefs.getLong(startedAtKey, 0)
        } returns started.startedAt.toEpochMilli()
        every {
            prefs.getLong(pausedForKey, 0)
        } returns started.pausedFor.toMillis()
        every {
            prefs.contains(pausedAtKey)
        } returns false

        assertEquals(started, property)
    }

    @Test
    fun `getValue should read stopped`() {
        every {
            prefs.contains(startedAtKey)
        } returns false
        every {
            prefs.contains(pausedAtKey)
        } returns false

        assertEquals(stopped, property)
    }

    private fun nowMilli(): Instant = ofEpochMilli(now().toEpochMilli())
}

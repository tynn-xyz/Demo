package demo.timer.app.prefs

import android.content.SharedPreferences
import androidx.core.content.edit
import demo.timer.core.Property
import demo.timer.core.Timer
import demo.timer.core.Timer.*
import demo.timer.core.Timer.Factory.paused
import demo.timer.core.Timer.Factory.started
import demo.timer.core.Timer.Factory.stopped
import java.time.Duration
import java.time.Duration.ZERO
import java.time.Duration.ofMillis
import java.time.Instant
import java.time.Instant.ofEpochMilli
import javax.inject.Inject
import kotlin.reflect.KProperty

internal class TimerProperty @Inject constructor(
    private val prefs: SharedPreferences,
) : Property<Timer> {

    private val startedAtKey = "timer.started.at"
    private val pausedForKey = "timer.paused.for"
    private val pausedAtKey = "timer.paused.at"

    override fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: Timer,
    ) = prefs.setTimer(value)

    override fun getValue(
        thisRef: Any?, property: KProperty<*>,
    ) = prefs.getTimer()

    private fun SharedPreferences.setTimer(
        timer: Timer,
    ) = edit {
        when (timer) {
            is Paused -> {
                setInstant(startedAtKey, timer.startedAt)
                setInstant(pausedAtKey, timer.pausedAt)
                setDuration(pausedForKey, timer.pausedFor)
            }
            is Started -> {
                setInstant(startedAtKey, timer.startedAt)
                setDuration(pausedForKey, timer.pausedFor)
                remove(pausedAtKey)
            }
            is Stopped -> {
                remove(startedAtKey)
                remove(pausedForKey)
                remove(pausedAtKey)
            }
        }
    }

    private fun SharedPreferences.getTimer(): Timer {
        val startedAt = getInstant(startedAtKey) ?: return stopped()
        val pausedFor = getDuration(pausedForKey) ?: ZERO
        val pausedAt = getInstant(pausedAtKey) ?: return started(
            startedAt = startedAt,
            pausedFor = pausedFor,
        )
        return paused(
            startedAt = startedAt,
            pausedAt = pausedAt,
            pausedFor = pausedFor,
        )
    }

    private fun SharedPreferences.Editor.setDuration(
        key: String,
        value: Duration,
    ) = putLong(
        key,
        value.toMillis(),
    )

    private fun SharedPreferences.Editor.setInstant(
        key: String,
        value: Instant,
    ) = putLong(
        key,
        value.toEpochMilli(),
    )

    private fun SharedPreferences.getDuration(
        key: String,
    ) = ofMillis(
        getLong(key, 0),
    )

    private fun SharedPreferences.getInstant(
        key: String,
    ) = if (contains(key)) ofEpochMilli(
        getLong(key, 0),
    ) else null
}

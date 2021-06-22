package demo.timer.app

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import demo.timer.app.R.id.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class AppTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(
        MainActivity::class.java,
    )

    @Before
    fun stop() {
        onView(withId(stop))
            .perform(click())
    }

    @Test
    fun start_pause_start_stop() {
        onView(withId(timer))
            .check(matches(withText("0s")))

        onView(withId(start))
            .perform(click())

        Thread.sleep(1_333)

        onView(withId(timer))
            .check(matches(withText("1s")))

        onView(withId(pause))
            .perform(click())

        Thread.sleep(1_000)

        onView(withId(timer))
            .check(matches(withText("1s")))

        onView(withId(start))
            .perform(click())

        Thread.sleep(1_000)

        onView(withId(timer))
            .check(matches(withText("2s")))

        onView(withId(stop))
            .perform(click())

        onView(withId(timer))
            .check(matches(withText("0s")))
    }
}

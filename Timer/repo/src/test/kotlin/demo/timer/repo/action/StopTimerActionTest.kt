package demo.timer.repo.action

import demo.timer.repo.store.TimerStore
import io.mockk.coVerifyAll
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

internal class StopTimerActionTest {

    private val state = mockk<TimerStore>(relaxed = true)

    private val action = StopTimerAction(state)

    @Test
    fun `action should delegate to state stop`() {
        runBlocking { action() }

        coVerifyAll { state.stopTimer() }
    }
}

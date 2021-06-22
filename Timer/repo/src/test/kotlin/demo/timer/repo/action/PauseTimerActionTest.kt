package demo.timer.repo.action

import demo.timer.repo.store.TimerStore
import io.mockk.coVerifyAll
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

internal class PauseTimerActionTest {

    val state = mockk<TimerStore>(relaxed = true)

    val action = PauseTimerAction(state)

    @Test
    fun `action should delegate to state pause`() {
        runBlocking { action() }

        coVerifyAll { state.pauseTimer() }
    }
}

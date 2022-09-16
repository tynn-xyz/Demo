package demo.timer.repo.flow

import demo.timer.core.Timer
import demo.timer.repo.store.TimerStore
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TimerFlowTest {

    private val states = listOf<Timer>(mockk(), mockk())
    private val state = mockk<TimerStore> {
        every { flow } returns states.asFlow()
    }

    private val flow = TimerFlow(state)

    @Test
    fun `flow should delegate to state flow`() {
        assertEquals(
            states,
            runBlocking { flow.toList() },
        )
    }
}

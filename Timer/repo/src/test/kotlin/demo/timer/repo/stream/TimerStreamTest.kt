package demo.timer.repo.stream

import demo.timer.core.Timer
import demo.timer.repo.store.TimerStore
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TimerStreamTest {

    val states = listOf<Timer>(mockk(), mockk())
    val state = mockk<TimerStore> {
        every { flow } returns states.asFlow()
    }

    val stream = TimerStream(state)

    @Test
    fun `flow should delegate to state flow`() {
        assertEquals(
            states,
            runBlocking { stream.toList() },
        )
    }
}

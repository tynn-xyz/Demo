package demo.timer.app.timer

import demo.timer.core.Action
import demo.timer.core.Timer
import demo.timer.core.Timer.*
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

internal class TimerViewModelTest {

    val dispatcher = TestCoroutineDispatcher()
    val scope = CoroutineScope(dispatcher)

    val stream = MutableStateFlow<Timer?>(null)
    val factory = mockk<TimerDuration.Factory>(relaxed = true)

    val pauseAction = mockk<Action>()
    val startAction = mockk<Action>()
    val stopAction = mockk<Action>()

    val viewModel by lazy {
        TimerViewModel(
            timerStream = stream.filterNotNull(),
            pauseAction = pauseAction,
            startAction = startAction,
            stopAction = stopAction,
            createState = factory,
        )
    }

    val paused = mockk<Paused>()
    val started = mockk<Started>()
    val stopped = mockk<Stopped>()

    @BeforeTest
    fun setupMain() = Dispatchers.setMain(dispatcher)

    @AfterTest
    fun resetMain() = Dispatchers.resetMain()

    @AfterTest
    fun closeScope() = scope.coroutineContext.cancelChildren()

    @Test
    fun `timer should be shared`() {
        stream.value = stopped
        scope.launch { viewModel.timer.collect() }
        scope.launch { viewModel.timer.collect() }

        verify(exactly = 1) { factory.invoke(stopped) }
    }

    @Test
    fun `timer should map paused once`() {
        stream.value = paused
        scope.launch { viewModel.timer.collect() }

        dispatcher.advanceTimeBy(999)

        verify(exactly = 1) { factory.invoke(paused) }
    }

    @Test
    fun `timer should map started more than once a second`() {
        stream.value = started
        scope.launch { viewModel.timer.collect() }

        dispatcher.advanceTimeBy(999)

        verify(atLeast = 3) { factory.invoke(started) }
    }

    @Test
    fun `timer should map stopped once`() {
        stream.value = stopped
        scope.launch { viewModel.timer.collect() }

        dispatcher.advanceTimeBy(999)

        verify(exactly = 1) { factory.invoke(stopped) }
    }

    @Test
    fun `pause should delegate to action`() {
        coEvery { pauseAction.invoke() } just runs

        runBlocking { viewModel.pause() }

        coVerifyAll { pauseAction.invoke() }
    }

    @Test
    fun `start should delegate to action`() {
        coEvery { startAction.invoke() } just runs

        runBlocking { viewModel.start() }

        coVerifyAll { startAction.invoke() }
    }

    @Test
    fun `stop should delegate to action`() {
        coEvery { stopAction.invoke() } just runs

        runBlocking { viewModel.stop() }

        coVerifyAll { stopAction.invoke() }
    }
}

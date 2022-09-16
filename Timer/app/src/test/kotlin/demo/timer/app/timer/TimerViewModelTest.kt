package demo.timer.app.timer

import demo.timer.core.Action
import demo.timer.core.Timer
import demo.timer.core.Timer.*
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class TimerViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private val scope = CoroutineScope(dispatcher)

    private val flow = MutableStateFlow<Timer?>(null)
    private val factory = mockk<TimerDuration.Factory>(relaxed = true)

    private val pauseAction = mockk<Action>()
    private val startAction = mockk<Action>()
    private val stopAction = mockk<Action>()

    private val viewModel by lazy {
        TimerViewModel(
            timerFlow = flow.filterNotNull(),
            pauseAction = pauseAction,
            startAction = startAction,
            stopAction = stopAction,
            createState = factory,
        )
    }

    private val paused = mockk<Paused>()
    private val started = mockk<Started>()
    private val stopped = mockk<Stopped>()

    @BeforeTest
    fun setupMain() = Dispatchers.setMain(dispatcher)

    @AfterTest
    fun resetMain() = Dispatchers.resetMain()

    @AfterTest
    fun closeScope() = scope.coroutineContext.cancelChildren()

    @Test
    fun `timer should be shared`() {
        flow.value = stopped
        viewModel.timer.launchIn(scope)
        viewModel.timer.launchIn(scope)

        verify(exactly = 1) { factory.invoke(stopped) }
    }

    @Test
    fun `timer should map paused once`() {
        flow.value = paused
        viewModel.timer.launchIn(scope)

        dispatcher.scheduler.advanceTimeBy(999)

        verify(exactly = 1) { factory.invoke(paused) }
    }

    @Test
    fun `timer should map started more than once a second`() {
        flow.value = started
        viewModel.timer.launchIn(scope)

        dispatcher.scheduler.advanceTimeBy(999)

        verify(atLeast = 3) { factory.invoke(started) }
    }

    @Test
    fun `timer should map stopped once`() {
        flow.value = stopped
        viewModel.timer.launchIn(scope)

        dispatcher.scheduler.advanceTimeBy(999)

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

package demo.timer.app.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import demo.timer.core.Action
import demo.timer.core.Timer
import demo.timer.core.Timer.*
import demo.timer.core.di.Pause
import demo.timer.core.di.Start
import demo.timer.core.di.Stop
import demo.timer.core.launchIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    timerFlow: Flow<Timer>,
    @Pause private val pauseAction: Action,
    @Start private val startAction: Action,
    @Stop private val stopAction: Action,
    private val createState: TimerDuration.Factory,
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val timer = timerFlow.flatMapLatest {
        when (it) {
            is Paused -> flowOf(createState(it))
            is Started -> flow {
                while (true) {
                    emit(createState(it))
                    delay(333)
                }
            }
            is Stopped -> flowOf(createState(it))
        }
    }.shareIn(viewModelScope, WhileSubscribed(), 1)

    fun pause() = pauseAction.launchIn(
        viewModelScope,
    )

    fun start() = startAction.launchIn(
        viewModelScope,
    )

    fun stop() = stopAction.launchIn(
        viewModelScope,
    )
}

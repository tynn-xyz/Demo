package demo.timer.repo

import dagger.Binds
import dagger.Module
import demo.timer.core.Action
import demo.timer.core.Stream
import demo.timer.core.Timer
import demo.timer.core.di.Pause
import demo.timer.core.di.Start
import demo.timer.core.di.Stop
import demo.timer.repo.action.PauseTimerAction
import demo.timer.repo.action.StartTimerAction
import demo.timer.repo.action.StopTimerAction
import demo.timer.repo.stream.TimerStream

@Module(includes = [BindsModule::class])
class RepoModule

@Module
private interface BindsModule {

    @[Binds Pause]
    fun bindsPauseAction(
        action: PauseTimerAction,
    ): Action

    @[Binds Start]
    fun bindsStartAction(
        action: StartTimerAction,
    ): Action

    @[Binds Stop]
    fun bindsStopAction(
        action: StopTimerAction,
    ): Action

    @Binds
    fun bindsTimerFlow(
        stream: TimerStream,
    ): Stream<Timer>
}

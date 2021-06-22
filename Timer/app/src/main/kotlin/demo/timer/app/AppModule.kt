package demo.timer.app

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.migration.DisableInstallInCheck
import demo.timer.app.prefs.TimerProperty
import demo.timer.core.Property
import demo.timer.core.Timer
import demo.timer.repo.RepoModule
import java.time.Clock
import java.time.Clock.systemDefaultZone

@Module(includes = [BindsModule::class, RepoModule::class])
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun providesClock(): Clock = systemDefaultZone()

    @Provides
    fun providesSharedPreferences(
        @ApplicationContext context: Context,
    ): SharedPreferences = context.getSharedPreferences(
        "app-prefs",
        MODE_PRIVATE,
    )
}

@[Module DisableInstallInCheck]
private interface BindsModule {

    @Binds
    fun bindsTimerProperty(
        property: TimerProperty,
    ): Property<Timer>
}

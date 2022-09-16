package demo.realm.data

import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import demo.realm.data.BuildConfig.REALM_APP_ID
import demo.realm.data.ktx.currentUserFlow
import demo.realm.data.ktx.getRealmInstance
import demo.realm.data.ktx.loginAwait
import demo.realm.data.ktx.logoutAwait
import demo.realm.data.model.Credentials
import demo.realm.data.model.CredentialsImpl
import demo.realm.data.utils.useMapLatest
import io.realm.mongodb.App
import io.realm.mongodb.Credentials.customFunction
import io.realm.mongodb.User
import kotlinx.coroutines.*
import kotlinx.coroutines.android.asCoroutineDispatcher
import kotlinx.coroutines.channels.BufferOverflow.DROP_OLDEST
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed

internal abstract class AppRealmImpl(name: String) {

    companion object : AppRealm {
        protected val app by lazy {
            App(REALM_APP_ID).apply {
                allUsers().values.firstOrNull {
                    it.isLoggedIn
                }?.let(::switchUser)
            }
        }

        override val isLoggedIn: Flow<Boolean>
            get() = app.currentUserFlow().map {
                it?.isLoggedIn == true
            }.distinctUntilChanged().retry { error ->
                errors.emit(error)
                true
            }

        override val errors = MutableSharedFlow<Throwable>(
            extraBufferCapacity = 1,
            onBufferOverflow = DROP_OLDEST,
        )

        override suspend operator fun plusAssign(credentials: Credentials) {
            withErrorHandler {
                app.currentUser()?.logoutAwait()
                val document = credentials as CredentialsImpl
                app.loginAwait(customFunction(document))
            }
        }

        @Suppress("UNUSED_PARAMETER")
        override suspend operator fun minusAssign(nothing: Nothing?) {
            withErrorHandler {
                app.currentUser()?.logoutAwait()
            }
        }

        protected suspend fun withErrorHandler(
            block: suspend () -> Unit,
        ) = try {
            block()
        } catch (error: Throwable) {
            Log.w("AppRealmImpl", error)
            errors.emit(error)
        }
    }

    private val scope by lazy {
        CoroutineScope(
            SupervisorJob() + Handler(
                HandlerThread(name).apply {
                    start()
                }.looper
            ).asCoroutineDispatcher(name)
        )
    }

    protected val currentUser: User?
        get() = app.currentUser()

    @OptIn(ExperimentalCoroutinesApi::class)
    protected fun realmFlow(
        partitionValue: User.() -> String?,
    ) = app.currentUserFlow().useMapLatest {
        it?.getRealmInstance(it.partitionValue()) {
            allowQueriesOnUiThread(false)
            allowWritesOnUiThread(false)
        }
    }

    protected fun <T> Flow<T>.share(
        stopTimeoutMillis: Long = 0,
        replayExpirationMillis: Long = 0,
    ) = retryWhen { error, attempt ->
        errors.emit(error)
        delay(attempt * attempt * 333)
        true
    }.shareIn(
        scope,
        WhileSubscribed(
            stopTimeoutMillis,
            replayExpirationMillis,
        ),
        replay = 1,
    )

    protected suspend fun launchInScope(
        block: suspend () -> Unit,
    ) = withContext(scope.coroutineContext) {
        withErrorHandler {
            block()
        }
    }
}

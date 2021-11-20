package demo.realm.app.fixtures

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlin.test.AfterTest

interface ResetMain {

    @AfterTest
    @ExperimentalCoroutinesApi
    fun resetMain() = Dispatchers.resetMain()

    @ExperimentalCoroutinesApi
    fun setMain()
}

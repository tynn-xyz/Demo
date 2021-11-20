package demo.realm.app.fixtures

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import kotlin.test.BeforeTest

interface UnconfinedMain : ResetMain {

    @BeforeTest
    @ExperimentalCoroutinesApi
    override fun setMain() = Dispatchers.setMain(Unconfined)
}

package demo.img.app

import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = ImagesApp::class)
class ImagesAppTest : KoinTest {

    @After
    fun teardown() {
        stopKoin()
    }

    @Test
    fun `onCreate should setup koin modules`() {
        getKoin().checkModules()
    }
}

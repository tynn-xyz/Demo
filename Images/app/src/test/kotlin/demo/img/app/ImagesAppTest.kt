package demo.img.app

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.lang.System.setProperty

@RunWith(RobolectricTestRunner::class)
@Config(application = ImagesApp::class)
class ImagesAppTest : KoinTest {

    @Before
    fun setup() {
        setProperty(
            "javax.net.ssl.trustStoreType",
            "JKS",
        )
    }

    @After
    fun teardown() {
        stopKoin()
    }

    @Test
    fun `onCreate should setup koin modules`() {
        getKoin().checkModules()
    }
}

package demo.realm.data.utils

import io.mockk.mockk
import io.mockk.verifyAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import java.io.Closeable
import kotlin.test.Test

internal class TransformTest {

    @Test
    @ExperimentalCoroutinesApi
    fun `useMapLatest should close used closeables`() {
        val closeable = mockk<Closeable>(relaxed = true)

        runBlocking {
            flowOf(true, false).useMapLatest {
                if (it) closeable else null
            }.collect()
        }

        verifyAll { closeable.close() }
    }
}

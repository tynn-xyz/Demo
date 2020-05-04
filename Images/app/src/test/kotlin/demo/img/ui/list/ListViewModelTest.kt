package demo.img.ui.list

import android.util.Log
import android.util.Log.w
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import demo.img.R
import demo.img.core.ImagesProvider
import demo.img.core.test.ImageData
import demo.img.model.Image
import demo.img.ui.list.ListState.*
import demo.img.url.UriWrapper
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class ListViewModelTest {

    @get:Rule
    val mainThread = InstantTaskExecutorRule()

    val provider = mockk<ImagesProvider>()
    lateinit var viewModel: ListViewModel

    @Before
    @ExperimentalCoroutinesApi
    fun setup() {
        Dispatchers.setMain(Unconfined)
        viewModel = ListViewModel(provider)
    }

    @Test
    fun `images should load from provider`() {
        val images = listOf(
            Image(
                "author1",
                mockk(),
                mockk(),
                mockk()
            ),
            Image(
                "author2",
                mockk(),
                mockk(),
                mockk()
            )
        )

        coEvery { provider(any()) } returns images.map { img ->
            ImageData(
                img.author,
                UriWrapper(img.web),
                UriWrapper(img.detail),
                UriWrapper(img.thumbnail)
            )
        }

        viewModel.state.collect {
            assertEquals(listOf(null, Data(images)), it)
        }

        coVerify { provider(1) }
    }

    @Test
    fun `images should post error`() {
        val error = IllegalStateException()
        coEvery { provider(any()) } throws error

        mockkStatic(Log::class) {
            every { w(any(), any<Throwable>()) } returns 0

            viewModel.state.collect {
                assertEquals(listOf(null, Error<Any>(R.string.message_loading_error)), it)
            }

            verify { w("ListViewModel", error) }
        }

        coVerify { provider(1) }
    }

    inline fun <T> LiveData<T>.collect(
        crossinline block: suspend (List<T>) -> Unit
    ) = runBlocking {
        val data = mutableListOf<T>()
        val observer = Observer<T> { data += it }
        observeForever(observer)
        try {
            block(data)
        } finally {
            removeObserver(observer)
        }
    }
}

package demo.img.app.ui.list

import android.os.Looper
import android.util.Log
import android.util.Log.w
import android.view.View
import android.view.View.OnClickListener
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStore
import androidx.recyclerview.widget.RecyclerView
import androidx.savedstate.SavedStateRegistry
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar.make
import demo.img.app.R.id.list_recycler
import demo.img.app.R.id.list_refresh
import demo.img.app.R.string.label_loading_retry
import demo.img.app.R.string.message_loading_error
import demo.img.app.model.Image
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

internal class ListFragmentTest {

    @get:Rule
    val mainThread = InstantTaskExecutorRule()

    private val adapter = mockk<ListAdapter>(relaxed = true)
    private val viewModel = mockk<ListViewModel>(relaxed = true)

    private val owner = mockk<LifecycleOwner>()

    private lateinit var fragment: ListFragment

    private val recycler = mockk<RecyclerView>(relaxed = true)
    private val refresh = mockk<SwipeRefreshLayout>(relaxed = true) {
        every { childCount } returns 1
        every { getChildAt(0) } returns this
        every {
            findViewById<View>(list_recycler)
        } returns recycler
        every {
            findViewById<View>(list_refresh)
        } returns this
    }

    private val view = refresh

    @Before
    fun setup() {
        mockkStatic(Log::class, Looper::class, Snackbar::class)
        mockkConstructor(SavedStateRegistry::class)
        every {
            anyConstructed<SavedStateRegistry>()
                .consumeRestoredStateForKey(any())
        } returns mockk(relaxed = true)
        startKoin {
            modules(module {
                factory { adapter }
                viewModel { viewModel }
            })
        }
        fragment = spyk {
            every { viewLifecycleOwner } returns owner
            every { viewModelStore } returns ViewModelStore()
            every { defaultViewModelCreationExtras } returns mockk(relaxed = true)
        }
    }

    @After
    fun teardown() {
        stopKoin()
        unmockkAll()
    }

    @Test
    fun `onViewCreated should setup grid with two columns`() {
        fragment.onViewCreated(view, null)

        verify {
            recycler.adapter = adapter
        }
    }

    @Test
    fun `onViewCreated should observe state data`() {
        fragment.onViewCreated(view, null)

        val observer = slot<Observer<Result<List<Image>>?>>()
        verify { viewModel.state.observe(owner, capture(observer)) }

        val list = mockk<List<Image>>()
        observer.captured.onChanged(success(list))

        verify { refresh.isRefreshing = false }
        verify { adapter.submitList(list) }
    }

    @Test
    fun `onViewCreated should observe state error`() {
        val snackbar = mockk<Snackbar> {
            every { setAction(any<Int>(), any()) } returns this
            every { show() } just runs
        }
        every { make(any(), any<Int>(), any()) } returns snackbar
        every { w(any(), any<Throwable>()) } returns 0

        fragment.onViewCreated(view, null)

        val observer = slot<Observer<Result<List<Image>>?>>()
        verify { viewModel.state.observe(owner, capture(observer)) }

        val throwable = Throwable()
        observer.captured.onChanged(failure(throwable))
        verify { w("ListFragment", throwable) }

        val listener = slot<OnClickListener>()
        verify { refresh.isRefreshing = false }
        verify {
            make(view, message_loading_error, LENGTH_LONG)
            snackbar.setAction(label_loading_retry, capture(listener))
            snackbar.show()
        }

        listener.captured.onClick(null)
        verify { viewModel.refresh() }
    }

    @Test
    fun `onViewCreated should observe state loading`() {
        every { Looper.getMainLooper() } returns mockk(relaxed = true)
        val snackbar = mockk<Snackbar> {
            every { setAction(any<Int>(), any()) } returns this
            every { show() } just runs
        }
        every { make(any(), any<Int>(), any()) } returns snackbar

        fragment.onViewCreated(view, null)

        val observer = slot<Observer<Result<List<Image>>?>>()
        verify { viewModel.state.observe(owner, capture(observer)) }

        observer.captured.onChanged(null)
        verify { refresh.isRefreshing = true }
    }

    @Test
    fun `onViewCreated should setup refresh delegate`() {
        fragment.onViewCreated(view, null)

        val listener = slot<OnRefreshListener>()
        verify { refresh.setOnRefreshListener(capture(listener)) }

        listener.captured.onRefresh()
        verify { viewModel.refresh() }
    }
}

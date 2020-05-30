package demo.img.ui.list

import android.util.Log
import android.util.Log.w
import android.view.View
import android.view.View.OnClickListener
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStore
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar.make
import demo.img.R
import demo.img.model.Image
import io.mockk.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.robolectric.RobolectricTestRunner
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@RunWith(RobolectricTestRunner::class)
internal class ListFragmentTest {

    val adapter = mockk<ListAdapter>(relaxed = true)
    val viewModel = mockk<ListViewModel>(relaxed = true)

    val owner = mockk<LifecycleOwner>()
    val store = ViewModelStore()

    val fragment = spyk<ListFragment> {
        every { viewLifecycleOwner } returns owner
        every { viewModelStore } returns store
    }

    val view = mockk<View> {
        every { list_recycler } returns mockk(relaxed = true)
        every { list_refresh } returns mockk(relaxed = true)
    }

    @Before
    fun setup() {
        startKoin {
            modules(module {
                factory { adapter }
                viewModel { viewModel }
            })
        }
    }

    @After
    fun teardown() {
        stopKoin()
    }

    @Test
    fun `onViewCreated should setup grid with two columns`() {
        val recycler = mockk<RecyclerView>(relaxed = true)
        every { view.list_recycler } returns recycler

        fragment.onViewCreated(view, null)

        verify {
            recycler.adapter = adapter
            recycler.layoutManager = match {
                it is GridLayoutManager && it.spanCount == 2
            }
        }
    }

    @Test
    fun `onViewCreated should observe state data`() {
        val refresh = mockk<SwipeRefreshLayout>(relaxed = true)
        every { view.list_refresh } returns refresh

        fragment.onViewCreated(view, null)

        val observer = slot<Observer<Result<List<Image>>?>>()
        verify { viewModel.state.observe(owner, capture(observer)) }

        val list = mockk<List<Image>>()
        observer.captured.onChanged(success(list))

        verify { refresh.isRefreshing = false }
        verify { adapter.submitList(list) }
    }

    @Test
    fun `onViewCreated should observe state error`() = mockkStatic(Log::class, Snackbar::class) {
        val refresh = mockk<SwipeRefreshLayout>(relaxed = true)
        every { view.list_refresh } returns refresh
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
            make(refresh, R.string.message_loading_error, LENGTH_LONG)
            snackbar.setAction(R.string.label_loading_retry, capture(listener))
            snackbar.show()
        }

        listener.captured.onClick(null)
        verify { viewModel.refresh() }
    }

    @Test
    fun `onViewCreated should observe state loading`() = mockkStatic(Snackbar::class) {
        val refresh = mockk<SwipeRefreshLayout>(relaxed = true)
        every { view.list_refresh } returns refresh
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
        val refresh = mockk<SwipeRefreshLayout>(relaxed = true)
        every { view.list_refresh } returns refresh

        fragment.onViewCreated(view, null)

        val listener = slot<OnRefreshListener>()
        verify { refresh.setOnRefreshListener(capture(listener)) }

        listener.captured.onRefresh()
        verify { viewModel.refresh() }
    }
}


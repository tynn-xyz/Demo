package demo.img.ui.list

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.squareup.picasso.Picasso
import demo.img.R.color.colorPrimaryLight
import demo.img.R.id.*
import demo.img.databinding.ViewThumbnailBinding
import demo.img.databinding.ViewThumbnailBinding.bind
import demo.img.databinding.ViewThumbnailBinding.inflate
import demo.img.view.SquareFrameLayout
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import xyz.tynn.hoppa.binding.BindingViewHolder

@RunWith(RobolectricTestRunner::class)
internal class ListAdapterTest {

    @get:Rule
    val mainThread = InstantTaskExecutorRule()

    val picasso = mockk<Picasso>(relaxed = true)

    val adapter = ListAdapter(picasso)

    val view = mockk<SquareFrameLayout> {
        every {
            findViewById<View>(list_item_caption)
        } returns mockk<TextView>(relaxed = true)
        every {
            findViewById<View>(list_item_thumbnail)
        } returns mockk<ImageView>(relaxed = true)
    }

    @Test
    fun `onCreateViewHolder should setup itemView`() = mockkStatic(
        LayoutInflater::class,
        ViewThumbnailBinding::class,
    ) {
        val context = mockk<Context>()
        val itemView = mockk<SquareFrameLayout>(relaxUnitFun = true)
        val parent = mockk<ViewGroup> {
            every { this@mockk.context } returns context
        }
        val inflater = mockk<LayoutInflater>()
        every { from(context) } returns inflater
        every { inflate(inflater, parent, false) } returns mockk {
            every { root } returns itemView
        }

        val holder = adapter.onCreateViewHolder(parent, 9)

        assertEquals(itemView, holder.itemView)
    }

    @Test
    fun `onBindViewHolder should load image into thumbnail with placeholder`() {
        val uri = mockk<Uri>()
        val image = mockk<ImageView>()
        every { view.findViewById<View>(list_item_thumbnail) } returns image
        val holder = BindingViewHolder(bind(view))
        adapter.submitList(listOf(mockk(relaxed = true) {
            every { thumbnail } returns uri
        }))

        adapter.onBindViewHolder(holder, 0)

        verify { picasso.load(uri).placeholder(colorPrimaryLight).into(image, any()) }
    }

    @Test
    fun `onBindViewHolder should set author as caption`() {
        val author = "author"
        val caption = mockk<TextView>(relaxed = true)
        every { view.findViewById<View>(list_item_caption) } returns caption
        val holder = BindingViewHolder(bind(view))
        adapter.submitList(listOf(mockk(relaxed = true) {
            every { this@mockk.author } returns author
        }))

        adapter.onBindViewHolder(holder, 0)

        verify { caption.text = author }
    }
}

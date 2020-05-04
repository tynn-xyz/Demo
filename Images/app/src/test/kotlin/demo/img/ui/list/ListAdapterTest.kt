package demo.img.ui.list

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.Navigation.setViewNavController
import androidx.navigation.findNavController
import com.squareup.picasso.Picasso
import demo.img.R
import io.mockk.*
import kotlinx.android.synthetic.main.view_thumbnail.view.*
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class ListAdapterTest {

    @get:Rule
    val mainThread = InstantTaskExecutorRule()

    val picasso = mockk<Picasso>(relaxed = true)

    val adapter = ListAdapter(picasso)

    @Test
    fun `onCreateViewHolder should setup itemView`() = mockkStatic(LayoutInflater::class) {
        val context = mockk<Context>()
        val itemView = mockk<View>(relaxUnitFun = true)
        val parent = mockk<ViewGroup> {
            every { this@mockk.context } returns context
        }
        every { LayoutInflater.from(context) } returns mockk {
            every { inflate(R.layout.view_thumbnail, parent, false) } returns itemView
        }

        val holder = adapter.onCreateViewHolder(parent, 9)

        assertEquals(itemView, holder.itemView)
    }

    @Test
    fun `onBindViewHolder should load image into thumbnail with placeholder`() {
        val uri = mockk<Uri>()
        val image = mockk<ImageView>()
        val holder = ListViewHolder(mockk {
            every { list_item_caption } returns mockk(relaxed = true)
            every { list_item_thumbnail } returns image
        })
        adapter.submitList(listOf(mockk(relaxed = true) {
            every { thumbnail } returns uri
        }))

        adapter.onBindViewHolder(holder, 0)

        verify { picasso.load(uri).placeholder(R.color.colorPlaceholder).into(image) }
    }

    @Test
    fun `onBindViewHolder should set author as caption`() {
        val author = "author"
        val caption = mockk<TextView>(relaxed = true)
        val holder = ListViewHolder(mockk {
            every { list_item_caption } returns caption
            every { list_item_thumbnail } returns mockk(relaxed = true)
        })
        adapter.submitList(listOf(mockk(relaxed = true) {
            every { this@mockk.author } returns author
        }))

        adapter.onBindViewHolder(holder, 0)

        verify { caption.text = author }
    }
}

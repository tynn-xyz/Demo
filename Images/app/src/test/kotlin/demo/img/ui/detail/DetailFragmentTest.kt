package demo.img.ui.detail

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri.parse
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.TextView
import androidx.navigation.NavController
import com.github.chrisbanes.photoview.PhotoView
import com.squareup.picasso.Picasso
import demo.img.R
import demo.img.model.Image
import io.mockk.*
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.view.*
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class DetailFragmentTest {

    val picasso = mockk<Picasso>(relaxed = true)

    val image = Image(
        "author",
        parse("https://example.com/thumbnail"),
        parse("https://example.com/detail"),
        parse("https://example.com/web")
    )

    val nav = mockk<NavController>(relaxed = true)

    val fragment = spyk<DetailFragment> {
        arguments = DetailFragmentArgs(image).toBundle()
        every { detail_image } returns mockk()
    }

    val view = mockk<View> {
        every { getTag(R.id.nav_controller_view_tag) } returns nav
        every { detail_image_caption } returns mockk(relaxed = true)
    }

    @Before
    fun setup() {
        startKoin {
            modules(module {
                factory { picasso }
            })
        }
    }

    @After
    fun teardown() {
        stopKoin()
    }

    @Test
    fun `onViewCreated should load image into photo view with placeholder`() {
        val photo = mockk<PhotoView>()
        every { fragment.detail_image } returns photo
        every { view.detail_image_caption } returns mockk(relaxed = true)

        fragment.onViewCreated(view, null)

        verify { picasso.load(image.detail).placeholder(R.color.colorPrimaryLight).into(photo, any()) }
    }

    @Test
    fun `onViewCreated should set author as caption`() {
        val caption = mockk<TextView>(relaxed = true)
        every { view.detail_image_caption } returns caption

        fragment.onViewCreated(view, null)

        verify { caption.text = image.author }
    }

    @Test
    fun `onDestroyView should cancel image loading`() {
        val photo = mockk<PhotoView>()
        every { fragment.detail_image } returns photo

        fragment.onDestroyView()

        verify { picasso.cancelRequest(photo) }
    }

    @Test
    fun `onCreateOptionsMenu should inflate menu`() {
        val inflater = mockk<MenuInflater>(relaxed = true)
        val menu = mockk<Menu>()

        fragment.onCreateOptionsMenu(menu, inflater)

        verify { inflater.inflate(R.menu.detail_menu, menu) }
    }

    @Test
    fun `onOptionsItemSelected should open web page`() {
        every { fragment.startActivity(any()) } just runs

        assertTrue(
            fragment.onOptionsItemSelected(mockk {
                every { itemId } returns R.id.menu_web
            })
        )

        verify {
            fragment.startActivity(match {
                Intent(ACTION_VIEW, image.web)
                    .filterEquals(it)
            })
        }
    }

    @Test
    fun `onOptionsItemSelected should not handle random`() {
        assertFalse(
            fragment.onOptionsItemSelected(mockk {
                every { itemId } returns android.R.id.home
            })
        )
    }
}

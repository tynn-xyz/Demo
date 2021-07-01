package demo.img.ui.detail

import android.R.id.home
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri.parse
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.navigation.NavController
import com.github.chrisbanes.photoview.PhotoView
import com.squareup.picasso.Picasso
import demo.img.R
import demo.img.R.color.colorPrimaryLight
import demo.img.R.id.*
import demo.img.model.Image
import io.mockk.*
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
        parse("https://example.com/web"),
    )

    val fragment by lazy { DetailFragment() }

    val caption = mockk<TextView>(relaxed = true)
    val photo = mockk<PhotoView>()

    val view = mockk<FrameLayout> {
        every {
            getTag(nav_controller_view_tag)
        } returns mockk<NavController>(relaxed = true)
        every {
            findViewById<View>(detail_image_caption)
        } returns caption
        every {
            findViewById<View>(detail_image)
        } returns photo
    }

    @Before
    fun setup() {
        mockkConstructor(DetailFragment::class)
        every {
            anyConstructed<DetailFragment>().arguments
        } returns DetailFragmentArgs(image).toBundle()
        every {
            anyConstructed<DetailFragment>().view
        } returns view
        every {
            anyConstructed<DetailFragment>().viewLifecycleOwner
        } returns mockk(relaxed = true)
        startKoin {
            modules(module {
                factory { picasso }
            })
        }
    }

    @After
    fun teardown() {
        unmockkAll()
        stopKoin()
    }

    @Test
    fun `onViewCreated should load image into photo view with placeholder`() {
        fragment.onViewCreated(view, null)

        verify { picasso.load(image.detail).placeholder(colorPrimaryLight).into(photo, any()) }
    }

    @Test
    fun `onViewCreated should set author as caption`() {
        fragment.onViewCreated(view, null)

        verify { caption.text = image.author }
    }

    @Test
    fun `onDestroyView should cancel image loading`() {
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
                every { itemId } returns menu_web
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
                every { itemId } returns home
            })
        )
    }
}

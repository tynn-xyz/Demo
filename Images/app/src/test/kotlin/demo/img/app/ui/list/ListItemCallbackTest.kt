package demo.img.app.ui.list

import android.net.Uri.parse
import demo.img.app.model.Image
import demo.img.app.ui.list.ListItemCallback.areContentsTheSame
import demo.img.app.ui.list.ListItemCallback.areItemsTheSame
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class ListItemCallbackTest {

    private val image11 = createImage(1, 1)
    private val image12 = createImage(1, 2)
    private val image21 = createImage(2, 1)
    private val image22 = createImage(2, 2)

    @Test
    fun `areItemsTheSame should check detail equality`() {
        assertTrue(areItemsTheSame(image11, image11))
        assertTrue(areItemsTheSame(image11, image12))
        assertFalse(areItemsTheSame(image11, image21))
        assertFalse(areItemsTheSame(image11, image22))

        assertTrue(areItemsTheSame(image12, image11))
        assertTrue(areItemsTheSame(image12, image12))
        assertFalse(areItemsTheSame(image12, image21))
        assertFalse(areItemsTheSame(image12, image22))

        assertFalse(areItemsTheSame(image21, image11))
        assertFalse(areItemsTheSame(image21, image12))
        assertTrue(areItemsTheSame(image21, image21))
        assertTrue(areItemsTheSame(image21, image22))

        assertFalse(areItemsTheSame(image22, image11))
        assertFalse(areItemsTheSame(image22, image12))
        assertTrue(areItemsTheSame(image22, image21))
        assertTrue(areItemsTheSame(image22, image22))
    }

    @Test
    fun `areContentsTheSame should check object equality`() {
        assertTrue(areContentsTheSame(image11, image11))
        assertFalse(areContentsTheSame(image11, image12))
        assertFalse(areContentsTheSame(image11, image21))
        assertFalse(areContentsTheSame(image11, image22))

        assertFalse(areContentsTheSame(image12, image11))
        assertTrue(areContentsTheSame(image12, image12))
        assertFalse(areContentsTheSame(image12, image21))
        assertFalse(areContentsTheSame(image12, image22))

        assertFalse(areContentsTheSame(image21, image11))
        assertFalse(areContentsTheSame(image21, image12))
        assertTrue(areContentsTheSame(image21, image21))
        assertFalse(areContentsTheSame(image21, image22))

        assertFalse(areContentsTheSame(image22, image11))
        assertFalse(areContentsTheSame(image22, image12))
        assertFalse(areContentsTheSame(image22, image21))
        assertTrue(areContentsTheSame(image22, image22))
    }

    private fun createImage(
        detail: Int,
        thumbnail: Int,
    ) = Image(
        "author$detail",
        parse("https://example.com/thumbnail/$thumbnail"),
        parse("https://example.com/detail/$detail"),
        parse("https://example.com/web/$detail"),
    )
}

package demo.img.app.url

import android.net.Uri.parse
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class UriParserTest {

    private val uri = "https://example.com/foo?bar=baz"

    private val parser = UriParser()

    @Test
    fun `invoke should parse Uri`() {
        assertEquals(UriWrapper(parse(uri)), parser(uri))
    }
}

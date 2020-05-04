package demo.img.url

import android.net.Uri
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

internal class UriWrapperTest {

    val uri = mockk<Uri>()
    val wrapper = UriWrapper(uri)

    @Test
    fun `toString should delegate to uri`() {
        val string = "https://example.com/string"
        every { uri.toString() } returns string

        assertEquals(string, wrapper.toString())
    }
}

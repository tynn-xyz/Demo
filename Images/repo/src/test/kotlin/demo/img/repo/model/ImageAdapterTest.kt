package demo.img.repo.model

import demo.img.core.test.StringUrl
import demo.img.repo.LoremImage
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

internal class ImageAdapterTest {

    val adapter = ImageAdapter(StringUrl)

    @Test
    fun `toData should convert ImageDto to LoremImageData`() {
        val dto = ImageDto(
            "author",
            1,
            2,
            "url",
            "download",
        )

        assertEquals(
            LoremImage(
                dto.author,
                StringUrl(
                    dto.download_url,
                ),
                StringUrl(
                    dto.download_url,
                ),
                StringUrl(
                    dto.url,
                ),
            ),
            with(adapter) {
                dto.toData()
            }
        )
    }

    @Test(expected = UnsupportedOperationException::class)
    fun `toDto should be unsupported`() {
        with(adapter) {
            mockk<LoremImage>().toDto()
        }
    }
}

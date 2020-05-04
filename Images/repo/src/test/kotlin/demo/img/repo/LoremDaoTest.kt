package demo.img.repo

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

internal class LoremDaoTest {

    val images = listOf(
        LoremImage(
            "1",
            mockk(),
            mockk(),
            mockk()
        ),
        LoremImage(
            "2",
            mockk(),
            mockk(),
            mockk()
        )
    )

    val service = mockk<LoremService> {
        coEvery { getImages(any()) } returns images
    }

    val dao = LoremDao(service)

    @Test
    fun `invoke page should map images`() = runBlocking {
        val page = 2

        assertEquals(images, dao(page))
        coVerify { service.getImages(page) }
    }
}

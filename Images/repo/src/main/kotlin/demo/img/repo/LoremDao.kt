package demo.img.repo

import demo.img.core.ImagesProvider

internal class LoremDao(
    private val service: LoremService
) : ImagesProvider {

    override suspend fun invoke(
        page: Int
    ) = service
        .getImages(page)
}

package demo.img.core

/**
 * Provides a page of [ImageData] from an undisclosed source.
 */
interface ImagesProvider : suspend (Int) -> List<ImageData> {
    override suspend fun invoke(page: Int): List<ImageData>
}

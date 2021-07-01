package demo.img.core

/**
 * Provides a page of [ImageData] from an undisclosed source.
 */
interface ImagesProvider {

    suspend operator fun invoke(
        page: Int,
    ): List<ImageData>
}

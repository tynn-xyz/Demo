package demo.img.core

/**
 * Entity with author and URLs for web, detail and thumbnail images.
 */
interface ImageData {

    val author: String

    val web: Url
    val detail: Url
    val thumbnail: Url

    override fun equals(
        other: Any?,
    ): Boolean
}

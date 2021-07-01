package demo.img.repo.model

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import demo.img.core.UrlParser
import demo.img.repo.LoremImage

internal class ImageAdapter(
    private val parseUrl: UrlParser,
) {

    @ToJson
    @Suppress("unused")
    fun LoremImage.toDto(): ImageDto =
        throw UnsupportedOperationException()

    @FromJson
    fun ImageDto.toData() = LoremImage(
        author = author,
        web = parseUrl(url),
        detail = parseUrl(downloadUrl()),
        thumbnail = parseUrl(downloadUrl(512F, 512F)),
    )

    private fun ImageDto.downloadUrl(): String {
        val scale = 25_000_000F / width / height
        return if (scale < 1)
            downloadUrl(width * scale, height * scale)
        else download_url
    }

    private fun ImageDto.downloadUrl(
        width: Float,
        height: Float,
    ) = download_url.replace(
        "/${this.width}/${this.height}",
        "/${width.toInt()}/${height.toInt()}",
    )
}

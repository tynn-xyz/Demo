package demo.img.repo.model

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import demo.img.core.UrlParser
import demo.img.repo.LoremImage

internal class ImageAdapter(
    private val parseUrl: UrlParser
) {

    @ToJson
    @Suppress("unused")
    fun LoremImage.toDto(): ImageDto =
        throw UnsupportedOperationException()

    @FromJson
    fun ImageDto.toData() = LoremImage(
        author = author,
        web = parseUrl(url),
        detail = parseUrl(download_url),
        thumbnail = parseUrl(
            download_url.replace(
                "/$width/$height",
                "/${512}/${512}"
            )
        )
    )
}

package demo.img.repo

import demo.img.core.ImageData
import demo.img.core.Url

internal data class LoremImage(
    override val author: String,
    override val detail: Url,
    override val thumbnail: Url,
    override val web: Url,
) : ImageData

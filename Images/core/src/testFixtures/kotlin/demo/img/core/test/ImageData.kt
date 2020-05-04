package demo.img.core.test

import demo.img.core.ImageData
import demo.img.core.Url

data class ImageData<U : Url>(
    override val author: String,
    override val web: U,
    override val detail: U,
    override val thumbnail: U
) : ImageData

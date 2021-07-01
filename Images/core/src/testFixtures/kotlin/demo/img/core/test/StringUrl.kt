package demo.img.core.test

import demo.img.core.Url
import demo.img.core.UrlParser

data class StringUrl(
    private val url: String,
) : Url {

    override fun toString() = url

    companion object : UrlParser {

        override fun invoke(
            url: String
        ) = StringUrl(url)
    }
}

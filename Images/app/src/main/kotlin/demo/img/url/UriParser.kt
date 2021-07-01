package demo.img.url

import android.net.Uri.parse
import demo.img.core.UrlParser

internal class UriParser : UrlParser {

    override fun invoke(
        url: String,
    ) = UriWrapper(
        parse(url),
    )
}

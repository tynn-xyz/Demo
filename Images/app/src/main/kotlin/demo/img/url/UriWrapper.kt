package demo.img.url

import android.net.Uri
import demo.img.core.Url

internal data class UriWrapper(
    val uri: Uri,
) : Url {

    override fun toString() =
        uri.toString()
}

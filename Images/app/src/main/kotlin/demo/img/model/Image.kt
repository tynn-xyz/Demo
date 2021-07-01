package demo.img.model

import android.net.Uri
import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@[Keep Parcelize]
data class Image(
    val author: String,
    val thumbnail: Uri,
    val detail: Uri,
    val web: Uri,
) : Parcelable

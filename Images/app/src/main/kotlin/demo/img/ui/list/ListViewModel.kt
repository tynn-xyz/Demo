package demo.img.ui.list

import android.net.Uri.parse
import android.util.Log.w
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import demo.img.R
import demo.img.core.ImageData
import demo.img.core.ImagesProvider
import demo.img.core.Url
import demo.img.model.Image
import demo.img.ui.list.ListState.*
import demo.img.url.UriWrapper

internal class ListViewModel(
    getImages: ImagesProvider
) : ViewModel() {

    private val refresh = MutableLiveData(Unit)

    val state = refresh.switchMap {
        liveData {
            try {
                emit(null)
                emit(Data(getImages(1).map { it.model }))
            } catch (e: Throwable) {
                w("ListViewModel", e)
                emit(Error<List<Image>>(R.string.message_loading_error))
            }
        }
    }

    private val ImageData.model
        get() = Image(
            author = author,
            thumbnail = thumbnail.uri,
            detail = detail.uri,
            web = web.uri
        )

    private val Url.uri
        get() = (this as? UriWrapper)?.uri
            ?: parse(toString())

    fun refresh() {
        refresh.value = Unit
    }
}

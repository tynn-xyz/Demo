package demo.img.ui.list

import androidx.annotation.StringRes

internal sealed class ListState<Model> {

    data class Data<Model>(
        val model: Model
    ) : ListState<Model>()

    data class Error<Model>(
        @StringRes
        val stringRes: Int
    ) : ListState<Model>()
}

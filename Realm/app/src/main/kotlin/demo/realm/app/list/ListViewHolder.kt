package demo.realm.app.list

import android.view.ViewGroup
import demo.realm.app.databinding.ItemListBinding
import xyz.tynn.hoppa.binding.BindingViewHolder

typealias ListViewHolder = BindingViewHolder<ItemListBinding>

@Suppress("FunctionName")
inline fun ListViewHolder(
    parent: ViewGroup,
    crossinline init: ListViewHolder.() -> Unit = {},
) = BindingViewHolder(
    parent = parent,
    inflate = ItemListBinding::inflate,
    init = init,
)

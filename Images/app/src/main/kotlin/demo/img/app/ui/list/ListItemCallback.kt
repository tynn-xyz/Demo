package demo.img.app.ui.list

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import demo.img.app.model.Image

internal object ListItemCallback : ItemCallback<Image>() {

    override fun areItemsTheSame(
        oldItem: Image,
        newItem: Image,
    ) = oldItem.web == newItem.web

    override fun areContentsTheSame(
        oldItem: Image,
        newItem: Image,
    ) = oldItem == newItem
}

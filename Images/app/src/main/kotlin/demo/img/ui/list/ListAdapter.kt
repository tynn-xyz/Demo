package demo.img.ui.list

import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Picasso
import demo.img.R
import demo.img.model.Image
import demo.img.ui.list.ListFragmentDirections.Companion.showDetailScreen
import kotlinx.android.synthetic.main.view_thumbnail.*

internal class ListAdapter(
    private val picasso: Picasso
) : ListAdapter<Image, ListViewHolder>(
    ListItemCallback
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ListViewHolder(
        from(parent.context).inflate(
            R.layout.view_thumbnail,
            parent,
            false
        )
    ).apply {
        itemView.setOnClickListener {
            it.findNavController().navigate(
                showDetailScreen(
                    getItem(adapterPosition)
                )
            )
        }
    }

    override fun onBindViewHolder(
        holder: ListViewHolder,
        position: Int
    ) = with(getItem(position)) {
        holder.list_item_caption.text = author
        picasso.load(thumbnail)
            .placeholder(R.color.colorPlaceholder)
            .into(holder.list_item_thumbnail)
    }
}

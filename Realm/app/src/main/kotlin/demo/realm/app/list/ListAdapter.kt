package demo.realm.app.list

import android.util.Log
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import demo.realm.app.R.id.showItemDialog
import demo.realm.app.utils.ITEM_CALLBACK
import demo.realm.data.model.Item
import xyz.tynn.hoppa.recycler.setOnClickListener

class ListAdapter : ListAdapter<Item, ListViewHolder>(ITEM_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ) = ListViewHolder(
        parent,
    ) { showItemDialogOnClick() }

    override fun onBindViewHolder(
        holder: ListViewHolder,
        position: Int,
    ): Unit = with(getItem(position)) {
        with(holder.binding) {
            itemValue.text = value
        }
    }

    private fun ListViewHolder.showItemDialogOnClick() {
        setOnClickListener {
            it.runCatching {
                with(getItem(bindingAdapterPosition)) {
                    findNavController().navigate(
                        showItemDialog,
                        bundleOf(
                            "itemId" to itemId,
                            "value" to value,
                        ),
                    )
                }
            }.onFailure {
                Log.w("ListAdapter", it)
            }
        }
    }
}

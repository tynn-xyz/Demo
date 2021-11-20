package demo.realm.app.item

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import demo.realm.data.model.Item
import xyz.tynn.hoppa.delegate.getValue
import xyz.tynn.hoppa.delegate.setValue
import java.util.*

class ItemViewModel(
    state: SavedStateHandle,
) : ViewModel() {

    private val itemId: UUID? by state
    var value: String? by state

    val isNewItem get() = itemId == null

    fun getItem(): Item? {
        return Item(
            itemId,
            value?.trim().takeUnless {
                it.isNullOrEmpty()
            } ?: return null,
        )
    }
}

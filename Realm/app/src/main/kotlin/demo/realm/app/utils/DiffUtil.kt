@file:[JvmMultifileClass JvmName("UtilsKt")]

package demo.realm.app.utils

import demo.realm.data.model.Item
import xyz.tynn.hoppa.recycler.DiffUtilItemCallback

val ITEM_CALLBACK = DiffUtilItemCallback<Item>(
    areItemsTheSame = { oldItem, newItem ->
        oldItem.itemId == newItem.itemId
    },
    areContentsTheSame = { oldItem, newItem ->
        oldItem.value == newItem.value
    },
)

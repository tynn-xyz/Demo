package demo.realm.data.model

import java.util.*
import java.util.UUID.randomUUID

sealed interface Item {
    val itemId: UUID
    val value: String
}

fun Item(
    itemId: UUID?,
    value: String,
): Item = ItemImpl(
    itemId = itemId
        ?: randomUUID(),
    value = value,
)

private data class ItemImpl(
    override val itemId: UUID,
    override val value: String,
) : Item

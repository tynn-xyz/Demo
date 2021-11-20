package demo.realm.app.utils

import demo.realm.data.model.Item
import java.util.UUID.randomUUID
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class DiffUtilTest {

    private val itemId = randomUUID()

    @Test
    fun `ITEM_CALLBACK areItemsTheSame should compare the itemId`() {
        assertFalse(
            ITEM_CALLBACK.areItemsTheSame(
                Item(null, "value"),
                Item(null, "value"),
            )
        )
        assertFalse(
            ITEM_CALLBACK.areItemsTheSame(
                Item(null, "value1"),
                Item(null, "value2"),
            )
        )
        assertTrue(
            ITEM_CALLBACK.areItemsTheSame(
                Item(itemId, "value"),
                Item(itemId, "value"),
            )
        )
        assertTrue(
            ITEM_CALLBACK.areItemsTheSame(
                Item(itemId, "value1"),
                Item(itemId, "value2"),
            )
        )
    }

    @Test
    fun `ITEM_CALLBACK areContentsTheSame should compare the value`() {
        assertTrue(
            ITEM_CALLBACK.areContentsTheSame(
                Item(null, "value"),
                Item(null, "value"),
            )
        )
        assertFalse(
            ITEM_CALLBACK.areContentsTheSame(
                Item(null, "value1"),
                Item(null, "value2"),
            )
        )
        assertTrue(
            ITEM_CALLBACK.areContentsTheSame(
                Item(itemId, "value"),
                Item(itemId, "value"),
            )
        )
        assertFalse(
            ITEM_CALLBACK.areContentsTheSame(
                Item(itemId, "value1"),
                Item(itemId, "value2"),
            )
        )
    }

    @Test
    fun `ITEM_CALLBACK getChangePayload should always return null`() {
        assertNull(
            ITEM_CALLBACK.getChangePayload(
                Item(null, "value"),
                Item(null, "value"),
            )
        )
        assertNull(
            ITEM_CALLBACK.getChangePayload(
                Item(null, "value1"),
                Item(null, "value2"),
            )
        )
        assertNull(
            ITEM_CALLBACK.getChangePayload(
                Item(itemId, "value"),
                Item(itemId, "value"),
            )
        )
        assertNull(
            ITEM_CALLBACK.getChangePayload(
                Item(itemId, "value1"),
                Item(itemId, "value2"),
            )
        )
    }
}

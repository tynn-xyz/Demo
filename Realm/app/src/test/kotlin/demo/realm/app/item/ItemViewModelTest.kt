package demo.realm.app.item

import androidx.lifecycle.SavedStateHandle
import demo.realm.data.model.Item
import java.util.UUID.randomUUID
import kotlin.test.*

internal class ItemViewModelTest {

    private val state = SavedStateHandle()
    private val viewModel = ItemViewModel(state)

    @Test
    fun `isNewItem should be true if itemId is not set`() {
        state.set("itemId", null)

        assertTrue(viewModel.isNewItem)
    }

    @Test
    fun `isNewItem should be false if itemId is not set`() {
        state.set("itemId", randomUUID())

        assertFalse(viewModel.isNewItem)
    }

    @Test
    fun `value should delegate to state`() {
        val value = "value"

        assertNull(viewModel.value)

        viewModel.value = value

        assertEquals(value, viewModel.value)
        assertEquals(value, state["value"])
    }

    @Test
    fun `getItem should create new Item with itemId and value`() {
        val itemId = randomUUID()
        val value = "value"

        state.set("itemId", itemId)
        state.set("value", value)

        assertEquals(
            Item(
                itemId = itemId,
                value = value,
            ),
            viewModel.getItem(),
        )
    }

    @Test
    fun `getItem should create new Item with value and new itemId`() {
        val value = "value"

        state.set("itemId", null)
        state.set("value", value)

        assertEquals(
            value,
            viewModel.getItem()?.value,
        )
    }

    @Test
    fun `getItem should not create new Item without value`() {
        state.set("itemId", null)
        state.set("value", "  ")

        assertNull(viewModel.getItem())

        state.set("itemId", randomUUID())

        assertNull(viewModel.getItem())
    }
}

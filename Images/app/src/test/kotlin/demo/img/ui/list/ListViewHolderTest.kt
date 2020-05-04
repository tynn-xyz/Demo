package demo.img.ui.list

import android.view.View
import android.widget.ImageView
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

internal class ListViewHolderTest {

    val itemView = mockk<View> {
        every { findViewById<ImageView>(any()) } returns mockk()
    }

    val holder = ListViewHolder(itemView)

    @Test
    fun `containerView should be itemView`() {
        assertEquals(itemView, holder.itemView)
        assertEquals(itemView, holder.containerView)
    }
}

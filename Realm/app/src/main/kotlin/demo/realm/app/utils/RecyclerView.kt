@file:[JvmMultifileClass JvmName("UtilsKt")]

package demo.realm.app.utils

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.annotation.Dimension
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.State

fun RecyclerView.addItemMargins(
    @DimenRes margin: Int,
) = addItemDecoration(
    MarginItemDecoration(
        context.resources.getDimensionPixelOffset(
            margin,
        )
    )
)

private class MarginItemDecoration(
    @Dimension private val margin: Int,
) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: State,
    ) = outRect.set(
        margin,
        if (parent.getChildLayoutPosition(view) == 0)
            margin else 0,
        margin,
        margin,
    )
}

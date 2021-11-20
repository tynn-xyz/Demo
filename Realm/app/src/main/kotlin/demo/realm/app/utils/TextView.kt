@file:[JvmMultifileClass JvmName("UtilsKt")]

package demo.realm.app.utils

import android.view.KeyEvent.KEYCODE_ENTER
import android.view.inputmethod.EditorInfo.IME_NULL
import android.widget.TextView
import android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE as MULTI_LINE

inline fun TextView.setOnEditorActionListener(
    actionId: Int,
    crossinline editorAction: () -> Boolean,
) = setOnEditorActionListener { view, action, event ->
    when (action) {
        IME_NULL -> event.keyCode == KEYCODE_ENTER
                && view.inputType and MULTI_LINE == 0
        actionId -> true
        else -> false
    } && editorAction()
}

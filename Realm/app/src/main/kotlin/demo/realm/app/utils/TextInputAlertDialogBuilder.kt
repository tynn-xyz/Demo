package demo.realm.app.utils

import android.content.Context
import android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import androidx.viewbinding.ViewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TextInputAlertDialogBuilder(
    context: Context,
    @StyleRes
    overrideThemeResId: Int,
) : MaterialAlertDialogBuilder(
    context,
    overrideThemeResId,
) {

    fun setView(
        binding: ViewBinding,
    ) = setView(
        binding.root,
    )

    override fun create(): AlertDialog {
        return super.create().apply {
            window?.setSoftInputMode(
                SOFT_INPUT_STATE_VISIBLE,
            )
        }
    }
}

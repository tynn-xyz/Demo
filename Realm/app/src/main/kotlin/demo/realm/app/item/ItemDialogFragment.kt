package demo.realm.app.item

import android.os.Bundle
import android.text.Editable
import android.view.inputmethod.EditorInfo.IME_ACTION_SEND
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import demo.realm.app.MainViewModel
import demo.realm.app.R.string.*
import demo.realm.app.databinding.DialogItemBinding
import demo.realm.app.utils.TextInputAlertDialogBuilder
import demo.realm.app.utils.setOnEditorActionListener
import demo.realm.app.databinding.DialogItemBinding.inflate as DialogItemBinding

class ItemDialogFragment : DialogFragment() {

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val viewModel by viewModels<ItemViewModel>()

    override fun onCreateDialog(
        savedInstanceState: Bundle?,
    ) = TextInputAlertDialogBuilder(
        requireContext(),
        theme,
    ).apply {
        setView(
            DialogItemBinding(
                layoutInflater,
            ).setupViews()
        )
        setNeutralButton(
            action_cancel,
            null,
        )
        val isNewItem = viewModel.isNewItem
        setPositiveButton(
            if (isNewItem) action_item_add
            else action_item_update,
        ) { _, _ -> storeItem() }
        if (!isNewItem) setNegativeButton(
            action_item_delete,
        ) { _, _ -> deleteItem() }
    }.create()

    private fun DialogItemBinding.setupViews() = apply {
        itemValue.apply {
            text?.replace(viewModel.value)
            doAfterTextChanged {
                viewModel.value = it?.toString()
            }
            setOnEditorActionListener(IME_ACTION_SEND) {
                storeItem()
                dismiss()
                true
            }
        }
    }

    private fun Editable.replace(text: CharSequence?) {
        replace(0, length, text ?: "")
    }

    private fun deleteItem() {
        mainViewModel.deleteItem(
            viewModel.getItem()
                ?: return,
        )
    }

    private fun storeItem() {
        mainViewModel.storeItem(
            viewModel.getItem()
                ?: return,
        )
    }
}

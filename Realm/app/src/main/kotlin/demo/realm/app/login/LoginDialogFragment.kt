package demo.realm.app.login

import android.os.Bundle
import android.view.inputmethod.EditorInfo.IME_ACTION_SEND
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import demo.realm.app.MainViewModel
import demo.realm.app.R.string.action_cancel
import demo.realm.app.R.string.action_login
import demo.realm.app.databinding.DialogLoginBinding
import demo.realm.app.utils.TextInputAlertDialogBuilder
import demo.realm.app.utils.setOnEditorActionListener
import demo.realm.app.databinding.DialogLoginBinding.inflate as DialogLoginBinding

class LoginDialogFragment : DialogFragment() {

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateDialog(
        savedInstanceState: Bundle?,
    ) = TextInputAlertDialogBuilder(
        requireContext(),
        theme,
    ).apply {
        setView(
            DialogLoginBinding(
                layoutInflater,
            ).setupViews()
        )
        setNeutralButton(
            action_cancel,
            null,
        )
        setPositiveButton(
            action_login,
        ) { _, _ -> login() }
    }.create()

    private fun DialogLoginBinding.setupViews() = apply {
        loginUsername.apply {
            setText(viewModel.username)
            doAfterTextChanged {
                viewModel.username = it?.toString()
            }
        }
        loginPassword.apply {
            setText(viewModel.password)
            doAfterTextChanged {
                viewModel.password = it?.toString()
            }
            setOnEditorActionListener(IME_ACTION_SEND) {
                login()
                dismiss()
                true
            }
        }
    }

    private fun login() {
        mainViewModel.login(
            viewModel.getCredentials()
                ?: return,
        )
    }
}

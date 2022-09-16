package demo.img.app.ui.list

import android.os.Bundle
import android.util.Log.w
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar.make
import demo.img.app.R.layout.fragment_list
import demo.img.app.R.string.label_loading_retry
import demo.img.app.R.string.message_loading_error
import demo.img.app.databinding.FragmentListBinding
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment(fragment_list) {

    private val viewModel by viewModel<ListViewModel>()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) = with(FragmentListBinding.bind(view)) {
        val listAdapter = get<ListAdapter>()

        listRecycler.adapter = listAdapter

        listRefresh.apply {
            viewModel.state.observe(viewLifecycleOwner) {
                isRefreshing = if (it == null) true else {
                    listAdapter.submitList(
                        it.getOrElse { e ->
                            w("ListFragment", e)
                            make(this, message_loading_error, LENGTH_LONG)
                                .setAction(label_loading_retry) {
                                    viewModel.refresh()
                                }.show()
                            null
                        }
                    )
                    false
                }
            }
        }.setOnRefreshListener {
            viewModel.refresh()
        }
    }
}

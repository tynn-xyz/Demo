package demo.img.ui.list

import android.os.Bundle
import android.util.Log.w
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar.make
import demo.img.R
import kotlinx.android.synthetic.main.fragment_list.view.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment(R.layout.fragment_list) {

    private val viewModel by viewModel<ListViewModel>()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) = with(view) {
        val listAdapter = get<ListAdapter>()

        list_recycler.apply {
            adapter = listAdapter
            layoutManager = GridLayoutManager(context, 2)
        }

        list_refresh.apply {
            viewModel.state.observe(viewLifecycleOwner) {
                isRefreshing = if (it == null) true else {
                    listAdapter.submitList(
                        it.getOrElse { e ->
                            w("ListFragment", e)
                            make(this, R.string.message_loading_error, LENGTH_LONG)
                                .setAction(R.string.label_loading_retry) {
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

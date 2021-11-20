package demo.realm.app.list

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.annotation.Dimension
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import demo.realm.app.R.dimen.default_margin
import demo.realm.app.R.layout.fragment_list
import demo.realm.app.databinding.FragmentListBinding
import demo.realm.app.utils.addItemMargins
import demo.realm.app.utils.viewLifecycleScope
import demo.realm.data.UserRealm
import xyz.tynn.hoppa.binding.viewBinding
import xyz.tynn.hoppa.flow.collectIn

class ListFragment : Fragment(fragment_list) {

    private val binding by viewBinding(FragmentListBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.root.setupList()
    }

    private fun RecyclerView.setupList() {
        adapter = ListAdapter().apply {
            UserRealm.items.collectIn(viewLifecycleScope) {
                submitList(it)
            }
        }
        addItemMargins(default_margin)
    }
}

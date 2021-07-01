package demo.img.ui.detail

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import demo.img.R.color.colorPrimaryLight
import demo.img.R.id.menu_web
import demo.img.R.layout.fragment_detail
import demo.img.R.menu.detail_menu
import demo.img.color.PaletteTarget.Companion.into
import demo.img.databinding.FragmentDetailBinding
import org.koin.android.ext.android.inject
import xyz.tynn.hoppa.binding.viewBinding

class DetailFragment : Fragment(fragment_detail) {

    private val args by navArgs<DetailFragmentArgs>()
    private val picasso by inject<Picasso>()

    private val image get() = args.image

    private val binding by viewBinding {
        FragmentDetailBinding.bind(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) = with(image) {
        with(binding) {
            detailImageCaption.text = author
            picasso.load(detail)
                .placeholder(colorPrimaryLight)
                .into(detailImage, detailImageCaption)
        }
    }

    override fun onDestroyView() {
        picasso.cancelRequest(binding.detailImage)
        super.onDestroyView()
    }

    override fun onCreateOptionsMenu(
        menu: Menu,
        inflater: MenuInflater,
    ) = inflater.inflate(detail_menu, menu)

    override fun onOptionsItemSelected(
        item: MenuItem,
    ) = if (item.itemId == menu_web) {
        startActivity(
            Intent(ACTION_VIEW, image.web)
        )
        true
    } else false
}

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
import demo.img.R
import demo.img.color.PaletteTarget.Companion.into
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.view.*
import org.koin.android.ext.android.inject

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val args by navArgs<DetailFragmentArgs>()
    private val picasso by inject<Picasso>()

    private val image get() = args.image

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) = with(image) {
        val caption = view.detail_image_caption
        caption.text = author
        picasso.load(detail)
            .placeholder(R.color.colorPrimaryLight)
            .into(detail_image, caption)
    }

    override fun onDestroyView() {
        picasso.cancelRequest(detail_image)
        super.onDestroyView()
    }

    override fun onCreateOptionsMenu(
        menu: Menu,
        inflater: MenuInflater
    ) = inflater.inflate(R.menu.detail_menu, menu)

    override fun onOptionsItemSelected(
        item: MenuItem
    ) = if (item.itemId == R.id.menu_web) {
        startActivity(
            Intent(ACTION_VIEW, image.web)
        )
        true
    } else false
}

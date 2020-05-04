package demo.img.app

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import demo.img.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val nav get() = findNavController(R.id.main_nav_host)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionBarWithNavController(nav)
    }

    override fun onOptionsItemSelected(
        item: MenuItem
    ) = if (item.itemId == android.R.id.home)
        nav.popBackStack()
    else super.onOptionsItemSelected(item)
}

package demo.img.app

import android.R.id.home
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import demo.img.R.id.main_nav_host
import demo.img.R.layout.activity_main

class MainActivity : AppCompatActivity(activity_main) {

    private val nav get() = findNavController(main_nav_host)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionBarWithNavController(nav)
    }

    override fun onOptionsItemSelected(
        item: MenuItem
    ) = if (item.itemId == home)
        nav.popBackStack()
    else super.onOptionsItemSelected(item)
}

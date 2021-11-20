package demo.realm.app

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.snackbar.Snackbar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar.make
import demo.realm.app.R.id.*
import demo.realm.app.R.menu.menu_main
import demo.realm.app.R.string.message_error
import demo.realm.app.databinding.ActivityMainBinding
import demo.realm.data.AppRealm
import xyz.tynn.hoppa.binding.contentViewBinding
import xyz.tynn.hoppa.flow.collectIn

class MainActivity : AppCompatActivity() {

    private val binding by contentViewBinding(ActivityMainBinding::inflate)
    private val viewModel by viewModels<MainViewModel>()

    private val nav get() = findNavController(nav_host_fragment_content_main)
    private val navConfig by lazy { AppBarConfiguration(nav.graph) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(nav, navConfig)
        binding.setupViews()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(menu_main, menu)
        menu.setupMenuItems()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            action_login -> nav.navigate(showLoginDialog)
            action_logout -> viewModel.logout()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onSupportNavigateUp() = nav.navigateUp(navConfig)
            || super.onSupportNavigateUp()

    private fun ActivityMainBinding.setupViews() {
        AppRealm.errors.collectIn(lifecycleScope) {
            make(root, message_error, LENGTH_LONG).show()
        }
        actionItemAdd.apply {
            AppRealm.isLoggedIn.collectIn(lifecycleScope) {
                if (it) show() else hide()
            }
            setOnClickListener {
                nav.navigate(showItemDialog)
            }
        }
    }

    private fun Menu.setupMenuItems() {
        val login = findItem(action_login)
        val logout = findItem(action_logout)
        AppRealm.isLoggedIn.collectIn(lifecycleScope) {
            login.isVisible = !it
            logout.isVisible = it
        }
    }
}

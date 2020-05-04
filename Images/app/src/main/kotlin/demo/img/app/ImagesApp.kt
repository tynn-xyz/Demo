package demo.img.app

import android.annotation.SuppressLint
import android.app.Application
import demo.img.repo.repoModule
import demo.img.ui.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@SuppressLint("Registered")
class ImagesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ImagesApp)
            modules(appModule, repoModule, uiModule)
        }
    }
}

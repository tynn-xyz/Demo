package demo.img.app

import android.app.Application
import demo.img.repo.repoModule
import demo.img.ui.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ImagesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(appModule, repoModule, uiModule)
        }
    }
}

package demo.img.app.ui

import com.squareup.picasso.OkHttp3Downloader
import demo.img.app.ui.list.ListAdapter
import demo.img.app.ui.list.ListViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.squareup.picasso.Picasso.Builder as PicassoBuilder

val uiModule = module {

    factory {
        ListAdapter(get())
    }

    viewModel {
        ListViewModel(get())
    }

    single {
        PicassoBuilder(androidContext())
            .downloader(OkHttp3Downloader(get<OkHttpClient>()))
            .build()
    }
}

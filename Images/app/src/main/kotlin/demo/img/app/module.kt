package demo.img.app

import demo.img.app.url.UriParser
import demo.img.core.UrlParser
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    factory {
        androidContext().cacheDir
    }

    factory {
        UriParser()
    } bind UrlParser::class
}

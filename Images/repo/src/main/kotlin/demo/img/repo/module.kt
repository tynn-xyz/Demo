package demo.img.repo

import demo.img.core.ImagesProvider
import demo.img.repo.model.ImageAdapter
import okhttp3.Cache
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory.create
import com.squareup.moshi.Moshi.Builder as MoshiBuilder
import okhttp3.OkHttpClient.Builder as OkHttpClientBuilder
import retrofit2.Retrofit.Builder as RetrofitBuilder

val repoModule = module {

    factory {
        Cache(get(), 256 shl 20) // 256MB
    }

    factory {
        ImageAdapter(get())
    }

    factory {
        MoshiBuilder()
            .add(get<ImageAdapter>())
            .build()
    }

    single {
        OkHttpClientBuilder()
            .cache(get())
            .build()
    }

    factory {
        RetrofitBuilder()
            .addConverterFactory(create(get()))
            .baseUrl("https://picsum.photos/v2/")
            .client(get())
            .build()
    }

    single {
        get<Retrofit>().create(LoremService::class.java)
    }

    single {
        LoremDao(get())
    } bind ImagesProvider::class
}

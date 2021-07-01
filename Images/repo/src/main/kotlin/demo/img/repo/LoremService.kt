package demo.img.repo

import retrofit2.http.GET
import retrofit2.http.Query

internal interface LoremService {

    @GET("list")
    suspend fun getImages(
        @Query("page")
        page: Int,
    ): List<LoremImage>
}

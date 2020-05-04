package demo.img.repo.model

import com.squareup.moshi.JsonClass

/**
 * DTO for image meta data.
 */
@JsonClass(generateAdapter = true)
internal data class ImageDto(
    // val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    val download_url: String
)

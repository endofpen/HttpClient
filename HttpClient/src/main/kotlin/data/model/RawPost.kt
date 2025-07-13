package data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RawPost(
    val title: String,
    val body: String,
    val userId: Int
)


package data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponsePost(
    val id: Int,
    val title: String,
    @field:Json(name = "body")
    val content: String,
    val userId: Int
)


package data.remote.api

import data.model.RawPost
import data.model.ResponsePost
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") postId: Int): ResponsePost

    @GET("posts")
    suspend fun getPosts(): List<ResponsePost>

    @POST("posts")
    suspend fun createPost(@Body post: RawPost): ResponsePost
}


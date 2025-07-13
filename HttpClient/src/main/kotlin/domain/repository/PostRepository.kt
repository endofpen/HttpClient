package domain.repository

import data.model.RawPost
import data.model.ResponsePost
import data.remote.api.ApiService

class PostRepository(private val apiService: ApiService) {
    suspend fun getPosts(): List<ResponsePost> = apiService.getPosts()
    suspend fun getPostById(id: Int): ResponsePost = apiService.getPostById(id)
    suspend fun createPost(post: RawPost): ResponsePost = apiService.createPost(post)
}


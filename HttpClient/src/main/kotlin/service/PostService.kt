package service

import data.model.RawPost
import data.model.ResponsePost
import domain.repository.PostRepository

class PostService(private val repository: PostRepository) {
    suspend fun getAllPosts(): List<ResponsePost> = repository.getPosts()
    suspend fun getPost(id: Int): ResponsePost = repository.getPostById(id)
    suspend fun createNewPost(post: RawPost): ResponsePost = repository.createPost(post)
}


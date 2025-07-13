import config.MoshiConfig
import config.NetworkConfig
import data.remote.api.ApiService
import data.remote.interceptor.AuthInterceptor
import data.remote.interceptor.ErrorLoggingInterceptor
import domain.repository.PostRepository
import service.PostService

suspend fun main() {
    val baseUrl = "https://jsonplaceholder.typicode.com/"

    val moshi = MoshiConfig.create()
    val httpClient = NetworkConfig.createHttpClient(
        true,
        AuthInterceptor(),
        ErrorLoggingInterceptor()
    )
    val retrofit = NetworkConfig.createRetrofit(baseUrl, httpClient, moshi)
    val apiService = retrofit.create(ApiService::class.java)
    val postRepository = PostRepository(apiService)
    val postService = PostService(postRepository)

    val posts = postService.getAllPosts()
    println(posts)
}




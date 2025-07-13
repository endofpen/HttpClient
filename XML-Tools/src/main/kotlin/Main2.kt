import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

fun main(args: Array<String>) = runBlocking {
// Example usage of the OAuth API client
    val baseUrl = "https://api.example.com"
    val oauthToken = "your_oauth_token_here"

    val apiClient = OAuthApiClient(baseUrl, oauthToken)

    try {
        // First API call (GET)
        val firstCallResult = async(Dispatchers.IO) {
            apiClient.makeApiCall("/users")
        }

        // Wait for the first call to complete
        val firstResponse = firstCallResult.await()
        println("First API Call Response: $firstResponse")

        // Second API call (POST) - triggered after first call completes
        val secondCallResult = async(Dispatchers.IO) {
            val postBody = """
                {
                    "name": "John Doe",
                    "email": "john@example.com"
                }
            """.trimIndent()
            apiClient.makePostApiCall("/users", postBody)
        }

        // Wait for the second call to complete
        val secondResponse = secondCallResult.await()
        println("Second API Call Response: $secondResponse")

    } catch (e: Exception) {
        println("API Call Error: ${e.message}")
    }
}

class OAuthApiClient(private val baseUrl: String, private val oauthToken: String) {
    private val client = OkHttpClient()
    private val json = Json { ignoreUnknownKeys = true }

    // Function to make an authenticated API call
    suspend fun makeApiCall(endpoint: String): String {
        val request = Request.Builder()
            .url("$baseUrl$endpoint")
            .addHeader("Authorization", "Bearer $oauthToken")
            .addHeader("Content-Type", "application/json")
            .build()

        return try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw IOException("Unexpected code $response")
                }
                response.body?.string() ?: throw IOException("Empty response body")
            }
        } catch (e: IOException) {
            throw e
        }
    }

    // Function to make a POST request with a JSON body
    suspend fun makePostApiCall(endpoint: String, body: String): String {
        val requestBody = body.toRequestBody("application/json".toMediaTypeOrNull())
        val request = Request.Builder()
            .url("$baseUrl$endpoint")
            .post(requestBody)
            .addHeader("Authorization", "Bearer $oauthToken")
            .addHeader("Content-Type", "application/json")
            .build()

        return try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw IOException("Unexpected code $response")
                }
                response.body?.string() ?: throw IOException("Empty response body")
            }
        } catch (e: IOException) {
            throw e
        }
    }
}

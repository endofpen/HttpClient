package data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class ErrorLoggingInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (!response.isSuccessful) {
            println("Error in call to ${request.url}")
            println("Response code: ${response.code}")
            println("Response message: ${response.message}")
        }

        return response
    }

}

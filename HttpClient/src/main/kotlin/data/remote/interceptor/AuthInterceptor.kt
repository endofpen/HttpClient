package data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val headerName: String = "Content-type",
    private val headerValue: String = "application/json; charset=UTF-8"
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .header(headerName, headerValue)
            .build()

        return chain.proceed(request)
    }
}


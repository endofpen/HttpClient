package config

import com.squareup.moshi.Moshi
import config.SslConfig.configureToTrustAllCerts
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkConfig {

    fun createHttpClient(trustAllCerts: Boolean = false, vararg interceptors: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                interceptors.forEach { addInterceptor(it) }
                if (trustAllCerts) configureToTrustAllCerts()
            }
            .build()
    }

    fun createRetrofit(baseUrl: String, httpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(httpClient)
            .build()
    }

}

package config

import okhttp3.OkHttpClient
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object SslConfig {
    fun OkHttpClient.Builder.configureToTrustAllCerts(): OkHttpClient.Builder {
        val trustManagers = createTrustAllCerts()
        val sslContext = createSslContext(trustManagers)
        val trustManager = trustManagers[0] as X509TrustManager

        return this
            .sslSocketFactory(sslContext.socketFactory, trustManager)
            .hostnameVerifier { _, _ -> true }
    }

    private fun createTrustAllCerts(): Array<TrustManager> = arrayOf(
        object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        }
    )

    private fun createSslContext(trustManagers: Array<TrustManager>): SSLContext {
        return SSLContext.getInstance("TLS").apply {
            init(null, trustManagers, SecureRandom())
        }
    }
}

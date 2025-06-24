package linkletter.client.core.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.head
import io.ktor.http.isSuccess

class LinkletterNetwork {
    private val httpClient = createHttpClient()

    private fun createHttpClient(): HttpClient =
        HttpClient {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(HttpTimeout) {
                connectTimeoutMillis = TIMEOUT_MILLIS
                requestTimeoutMillis = TIMEOUT_MILLIS
                socketTimeoutMillis = TIMEOUT_MILLIS
            }
        }

    suspend fun fetchContent(url: String): String = httpClient.get(url).body()

    suspend fun checkUrlExists(url: String): Boolean =
        try {
            val response = httpClient.head(url)
            response.status.isSuccess()
        } catch (e: Exception) {
            false
        }

    companion object {
        private const val TIMEOUT_MILLIS = 6_000L
    }
}

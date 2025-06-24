package linkletter.client.core.domain.repository

interface RssUrlRepository {
    suspend fun findRssUrl(url: String): String?
}

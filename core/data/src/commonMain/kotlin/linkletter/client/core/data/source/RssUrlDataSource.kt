package linkletter.client.core.data.source

interface RssUrlDataSource {
    suspend fun getRssUrl(url: String): String?
}

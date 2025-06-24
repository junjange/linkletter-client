package linkletter.client.core.data.repository

import linkletter.client.core.data.source.RssUrlDataSource
import linkletter.client.core.domain.repository.RssUrlRepository

internal class DefaultRssUrlRepository(
    private val source: RssUrlDataSource,
) : RssUrlRepository {
    override suspend fun findRssUrl(url: String): String? = source.getRssUrl(url)
}

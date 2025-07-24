package linkletter.client.core.data.repository

import kotlinx.coroutines.flow.Flow
import linkletter.client.core.data.source.LatestPostDataSource
import linkletter.client.core.domain.repository.LatestPostRepository

internal class DefaultLatestPostRepository(
    private val source: LatestPostDataSource,
) : LatestPostRepository {
    override fun latestPostLink(blogUrl: String): Flow<String?> = source.latestPostLink(blogUrl = blogUrl)

    override suspend fun saveLatestPostLink(
        blogUrl: String,
        latestPostLink: String,
    ) = source.saveLatestPostLink(blogUrl = blogUrl, latestPostLink = latestPostLink)
}

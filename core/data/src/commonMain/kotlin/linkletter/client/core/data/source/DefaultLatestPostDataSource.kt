package linkletter.client.core.data.source

import kotlinx.coroutines.flow.Flow
import linkletter.client.core.datastore.LatestPostPreferences

internal class DefaultLatestPostDataSource(
    private val preferences: LatestPostPreferences,
) : LatestPostDataSource {
    override fun latestPostLink(blogUrl: String): Flow<String?> = preferences.latestPostLink(blogUrl = blogUrl)

    override suspend fun saveLatestPostLink(
        blogUrl: String,
        latestPostLink: String,
    ) = preferences.saveLatestPostLink(blogUrl = blogUrl, latestPostLink = latestPostLink)
}

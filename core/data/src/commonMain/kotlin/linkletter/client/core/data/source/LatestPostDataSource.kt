package linkletter.client.core.data.source

import kotlinx.coroutines.flow.Flow

interface LatestPostDataSource {
    fun latestPostLink(blogUrl: String): Flow<String?>

    suspend fun saveLatestPostLink(
        blogUrl: String,
        latestPostLink: String,
    )
}

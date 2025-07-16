package linkletter.client.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface LatestPostRepository {
    fun latestPostLink(blogUrl: String): Flow<String?>

    suspend fun saveLatestPostLink(
        blogUrl: String,
        latestPostLink: String,
    )
}

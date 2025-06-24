package linkletter.client.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import linkletter.client.core.data.source.RssDataSource
import linkletter.client.core.data.source.RssUrlDataSource
import linkletter.client.core.domain.repository.PostsRepository
import linkletter.client.core.model.BlogPlatform
import linkletter.client.core.model.Post

internal class DefaultPostsRepository(
    private val rssDataSource: RssDataSource,
    private val rssUrlDataSource: RssUrlDataSource,
) : PostsRepository {
    override fun fetchPosts(url: String): Flow<List<Post>> =
        flow {
            val rssUrl =
                rssUrlDataSource.getRssUrl(url = url)
                    ?: throw IllegalArgumentException("지원하지 않는 블로그 URL입니다.")

            val platform = BlogPlatform.from(rssUrl = rssUrl)
            val posts = rssDataSource.fetchPosts(platform = platform)

            emitAll(posts)
        }
}

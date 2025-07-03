package linkletter.client.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import linkletter.client.core.data.source.BlogInfoDataSource
import linkletter.client.core.data.source.RssDataSource
import linkletter.client.core.data.source.RssUrlDataSource
import linkletter.client.core.domain.repository.BlogRepository
import linkletter.client.core.model.Blog
import linkletter.client.core.model.BlogInfo
import linkletter.client.core.model.BlogPlatform

internal class DefaultBlogRepository(
    private val rssDataSource: RssDataSource,
    private val rssUrlDataSource: RssUrlDataSource,
    private val blogInfoDataSource: BlogInfoDataSource,
) : BlogRepository {
    override suspend fun insertBlogInfo(blogInfo: BlogInfo) {
        blogInfoDataSource.insertBlogInfo(blogInfo = blogInfo)
    }

    override suspend fun deleteBlogInfo(blogUrl: String) {
        blogInfoDataSource.deleteBlogInfo(blogUrl = blogUrl)
    }

    override fun getAllBlogInfos(): Flow<List<BlogInfo>> = blogInfoDataSource.getAllBlogInfos()

    override fun fetchBlogList(): Flow<List<Blog>> =
        flow {
            val blogInfos = getAllBlogInfos().first()
            val blogList =
                blogInfos.map { blogInfo ->
                    fetchBlog(blogUrl = blogInfo.url).first()
                }
            emit(blogList)
        }

    override fun fetchBlog(blogUrl: String): Flow<Blog> =
        flow {
            val rssUrl = resolveRssUrl(blogUrl)
            val platform = BlogPlatform.from(rssUrl)
            emitAll(rssDataSource.fetchPosts(platform))
        }

    private suspend fun resolveRssUrl(blogUrl: String): String =
        rssUrlDataSource.getRssUrl(blogUrl)
            ?: throw IllegalArgumentException("지원하지 않는 블로그 URL입니다: $blogUrl")
}

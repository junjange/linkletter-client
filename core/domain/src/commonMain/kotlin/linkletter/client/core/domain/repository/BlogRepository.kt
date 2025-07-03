package linkletter.client.core.domain.repository

import kotlinx.coroutines.flow.Flow
import linkletter.client.core.model.Blog
import linkletter.client.core.model.BlogInfo

interface BlogRepository {
    suspend fun insertBlogInfo(blogInfo: BlogInfo)

    suspend fun deleteBlogInfo(blogUrl: String)

    fun getAllBlogInfos(): Flow<List<BlogInfo>>

    fun fetchBlog(url: String): Flow<Blog>

    fun fetchBlogList(): Flow<List<Blog>>
}

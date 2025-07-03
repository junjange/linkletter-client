package linkletter.client.core.data.source

import kotlinx.coroutines.flow.Flow
import linkletter.client.core.model.BlogInfo

interface BlogInfoDataSource {
    fun getAllBlogInfos(): Flow<List<BlogInfo>>

    suspend fun getBlogInfoByUrl(blogUrl: String): BlogInfo?

    suspend fun insertBlogInfo(blogInfo: BlogInfo)

    suspend fun updateBlogInfo(blogInfo: BlogInfo)

    suspend fun deleteBlogInfo(blogUrl: String)

    suspend fun isBlogInfoExists(blogUrl: String): Boolean
}

package linkletter.client.core.data.source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import linkletter.client.core.database.dao.BlogInfoDao
import linkletter.client.core.database.mapper.toData
import linkletter.client.core.database.mapper.toEntity
import linkletter.client.core.model.BlogInfo

class DefaultBlogInfoInfoDataSource(
    private val dao: BlogInfoDao,
) : BlogInfoDataSource {
    override fun getAllBlogInfos(): Flow<List<BlogInfo>> =
        dao.getAllBlogInfos().map { blogInfoEntityList ->
            blogInfoEntityList.toData()
        }

    override suspend fun getBlogInfoByUrl(blogUrl: String): BlogInfo? = dao.getBlogInfoByUrl(blogUrl)?.toData()

    override suspend fun insertBlogInfo(blogInfo: BlogInfo) = dao.insertBlogInfo(blogInfo = blogInfo.toEntity())

    override suspend fun updateBlogInfo(blogInfo: BlogInfo) = dao.updateBlogInfo(blogInfo = blogInfo.toEntity())

    override suspend fun deleteBlogInfo(blogUrl: String) = dao.deleteBlogInfoByUrl(blogUrl)

    override suspend fun isBlogInfoExists(blogUrl: String): Boolean = dao.isBlogInfoExists(blogUrl)
}

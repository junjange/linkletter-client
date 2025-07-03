package linkletter.client.core.domain.repository

import kotlinx.coroutines.flow.Flow
import linkletter.client.core.model.Blog

interface BlogRepository {
    fun fetchBlog(url: String): Flow<Blog>
}

package linkletter.client.core.domain.usecase

import linkletter.client.core.domain.repository.BlogRepository
import linkletter.client.core.model.BlogInfo

internal class DefaultInsertBlogInfoUseCase(
    private val repository: BlogRepository,
) : InsertBlogInfoUseCase {
    override suspend fun invoke(blogInfo: BlogInfo) = repository.insertBlogInfo(blogInfo)
} 
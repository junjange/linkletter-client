package linkletter.client.core.domain.usecase

import linkletter.client.core.domain.repository.BlogRepository

internal class DefaultDeleteBlogInfoUseCase(
    private val repository: BlogRepository,
) : DeleteBlogInfoUseCase {
    override suspend fun invoke(blogUrl: String) = repository.deleteBlogInfo(blogUrl)
} 
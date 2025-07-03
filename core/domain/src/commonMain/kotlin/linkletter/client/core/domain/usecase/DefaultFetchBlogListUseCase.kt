package linkletter.client.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import linkletter.client.core.domain.repository.BlogRepository
import linkletter.client.core.model.Blog

internal class DefaultFetchBlogListUseCase(
    private val repository: BlogRepository,
) : FetchBlogListUseCase {
    override fun invoke(): Flow<List<Blog>> = repository.fetchBlogList()
} 
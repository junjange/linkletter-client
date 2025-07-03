package linkletter.client.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import linkletter.client.core.domain.repository.BlogRepository
import linkletter.client.core.model.Blog

internal class DefaultGetBlogUseCase(
    private val repository: BlogRepository,
) : GetBlogUseCase {
    override operator fun invoke(url: String): Flow<Blog> = repository.fetchBlog(url = url)
}

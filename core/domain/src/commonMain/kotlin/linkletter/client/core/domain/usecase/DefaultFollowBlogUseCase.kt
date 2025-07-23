package linkletter.client.core.domain.usecase

import linkletter.client.core.domain.repository.BlogRepository
import linkletter.client.core.model.Blog

internal class DefaultFollowBlogUseCase(
    private val repository: BlogRepository,
) : InsertBlogInfoUseCase {
    override suspend fun invoke(blog: Blog) {
        repository.saveNewFollowedBlog(blog = blog)
    }
}

package linkletter.client.core.domain.usecase

import linkletter.client.core.model.Blog

interface InsertBlogInfoUseCase {
    suspend operator fun invoke(blog: Blog)
}

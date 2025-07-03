package linkletter.client.core.domain.usecase

import linkletter.client.core.model.BlogInfo

interface InsertBlogInfoUseCase {
    suspend operator fun invoke(blogInfo: BlogInfo)
} 
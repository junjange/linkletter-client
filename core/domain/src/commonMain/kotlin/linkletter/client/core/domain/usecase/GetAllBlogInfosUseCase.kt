package linkletter.client.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import linkletter.client.core.model.BlogInfo

interface GetAllBlogInfosUseCase {
    operator fun invoke(): Flow<List<BlogInfo>>
} 
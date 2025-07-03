package linkletter.client.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import linkletter.client.core.domain.repository.BlogRepository
import linkletter.client.core.model.BlogInfo

internal class DefaultGetAllBlogInfosUseCase(
    private val repository: BlogRepository,
) : GetAllBlogInfosUseCase {
    override fun invoke(): Flow<List<BlogInfo>> = repository.getAllBlogInfos()
} 
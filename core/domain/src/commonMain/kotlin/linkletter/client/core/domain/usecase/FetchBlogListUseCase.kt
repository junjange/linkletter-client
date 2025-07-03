package linkletter.client.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import linkletter.client.core.model.Blog

interface FetchBlogListUseCase {
    operator fun invoke(): Flow<List<Blog>>
} 
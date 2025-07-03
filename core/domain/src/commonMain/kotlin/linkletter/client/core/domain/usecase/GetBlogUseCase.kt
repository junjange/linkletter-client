package linkletter.client.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import linkletter.client.core.model.Blog

interface GetBlogUseCase {
    operator fun invoke(url: String): Flow<Blog>
}

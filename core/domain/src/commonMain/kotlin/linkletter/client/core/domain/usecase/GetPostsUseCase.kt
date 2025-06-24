package linkletter.client.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import linkletter.client.core.model.Post

interface GetPostsUseCase {
    operator fun invoke(url: String): Flow<List<Post>>
}

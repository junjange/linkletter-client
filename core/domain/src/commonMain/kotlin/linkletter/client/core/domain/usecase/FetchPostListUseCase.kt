package linkletter.client.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import linkletter.client.core.model.Post

interface FetchPostListUseCase {
    operator fun invoke(query: String): Flow<List<Post>>
}

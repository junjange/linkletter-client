package linkletter.client.core.domain.usecase

import kotlinx.coroutines.flow.Flow

interface LatestPostLinkUseCase {
    operator fun invoke(blogUrl: String): Flow<String?>
}

package linkletter.client.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import linkletter.client.core.domain.repository.LatestPostRepository

internal class DefaultLatestPostLinkUseCase(
    private val repository: LatestPostRepository,
) : LatestPostLinkUseCase {
    override fun invoke(blogUrl: String): Flow<String?> = repository.latestPostLink(blogUrl = blogUrl)
}

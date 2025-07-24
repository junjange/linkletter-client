package linkletter.client.core.domain.usecase

import linkletter.client.core.domain.repository.LatestPostRepository

internal class DefaultSaveLatestPostLinkUseCase(
    private val repository: LatestPostRepository,
) : SaveLatestPostLinkUseCase {
    override suspend fun invoke(
        blogUrl: String,
        latestPostLink: String,
    ) = repository.saveLatestPostLink(blogUrl = blogUrl, latestPostLink = latestPostLink)
}

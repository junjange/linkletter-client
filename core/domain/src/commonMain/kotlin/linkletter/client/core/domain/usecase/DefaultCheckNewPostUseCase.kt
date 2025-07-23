package linkletter.client.core.domain.usecase

import kotlinx.coroutines.flow.firstOrNull

internal class DefaultCheckNewPostUseCase(
    private val getBlogUseCase: GetBlogUseCase,
    private val latestPostLinkUseCase: LatestPostLinkUseCase,
    private val saveLatestPostLinkUseCase: SaveLatestPostLinkUseCase,
) : CheckNewPostUseCase {
    override suspend operator fun invoke(blogUrl: String): String? {
        val remoteLink =
            getBlogUseCase(blogUrl)
                .firstOrNull()
                ?.postList
                ?.firstOrNull()
                ?.link
                ?: return null

        val localLink =
            latestPostLinkUseCase(blogUrl)
                .firstOrNull()

        return if (remoteLink != localLink) {
            saveLatestPostLinkUseCase(blogUrl, remoteLink)
            remoteLink
        } else {
            null
        }
    }
}

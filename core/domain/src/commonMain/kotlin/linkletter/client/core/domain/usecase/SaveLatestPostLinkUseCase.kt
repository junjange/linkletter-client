package linkletter.client.core.domain.usecase

interface SaveLatestPostLinkUseCase {
    suspend operator fun invoke(
        blogUrl: String,
        latestPostLink: String,
    )
}

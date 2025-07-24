package linkletter.client.core.domain.usecase

interface CheckNewPostUseCase {
    suspend operator fun invoke(blogUrl: String): String?
}

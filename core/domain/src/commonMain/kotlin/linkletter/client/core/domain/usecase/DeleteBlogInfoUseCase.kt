package linkletter.client.core.domain.usecase

interface DeleteBlogInfoUseCase {
    suspend operator fun invoke(blogUrl: String)
} 
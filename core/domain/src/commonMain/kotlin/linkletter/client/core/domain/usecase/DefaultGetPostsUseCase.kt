package linkletter.client.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import linkletter.client.core.domain.repository.PostsRepository
import linkletter.client.core.model.Post

internal class DefaultGetPostsUseCase(
    private val postsRepository: PostsRepository,
) : GetPostsUseCase {
    override operator fun invoke(url: String): Flow<List<Post>> = postsRepository.fetchPosts(url = url)
}

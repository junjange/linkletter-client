package linkletter.client.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import linkletter.client.core.domain.repository.BlogRepository
import linkletter.client.core.model.Post

internal class DefaultFetchPostListUseCase(
    private val repository: BlogRepository,
) : FetchPostListUseCase {
    override fun invoke(query: String): Flow<List<Post>> =
        repository
            .fetchBlogList()
            .map { blogList ->
                blogList
                    .flatMap { it.postList }
            }.distinctUntilChanged()
            .map { fullList ->
                if (query.isBlank()) {
                    fullList
                } else {
                    val q = query.trim()
                    fullList.filter { post ->
                        post.title.contains(q, ignoreCase = true) ||
                            post.description.contains(q, ignoreCase = true) ||
                            post.author.name.contains(q, ignoreCase = true)
                    }
                }
            }
}

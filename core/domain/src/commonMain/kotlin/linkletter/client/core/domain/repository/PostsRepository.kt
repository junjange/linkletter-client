package linkletter.client.core.domain.repository

import kotlinx.coroutines.flow.Flow
import linkletter.client.core.model.Post

interface PostsRepository {
    fun fetchPosts(url: String): Flow<List<Post>>
}

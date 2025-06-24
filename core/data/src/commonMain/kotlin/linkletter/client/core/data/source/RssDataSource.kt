package linkletter.client.core.data.source

import kotlinx.coroutines.flow.Flow
import linkletter.client.core.model.BlogPlatform
import linkletter.client.core.model.Post

interface RssDataSource {
    fun fetchPosts(platform: BlogPlatform): Flow<List<Post>>
}

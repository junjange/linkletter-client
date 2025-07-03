package linkletter.client.core.data.source

import kotlinx.coroutines.flow.Flow
import linkletter.client.core.model.Blog
import linkletter.client.core.model.BlogPlatform

interface RssDataSource {
    fun fetchPosts(platform: BlogPlatform): Flow<Blog>
}

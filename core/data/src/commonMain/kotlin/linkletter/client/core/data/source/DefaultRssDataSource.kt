package linkletter.client.core.data.source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import linkletter.client.core.data.parser.RssParserFactory
import linkletter.client.core.model.Blog
import linkletter.client.core.model.BlogPlatform
import linkletter.client.core.network.LinkletterNetwork

internal class DefaultRssDataSource(
    private val network: LinkletterNetwork,
) : RssDataSource {
    override fun fetchPosts(platform: BlogPlatform): Flow<Blog> =
        flow {
            emit(network.fetchContent(platform.rssUrl))
        }.map { xml ->
            val parser = RssParserFactory.getParserFor(platform)
            parser.parse(xml)
        }.catch {
            emit(Blog.Default)
        }
}

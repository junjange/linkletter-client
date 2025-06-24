package linkletter.client.core.data.source

import linkletter.client.core.data.parser.RssUrlParser
import linkletter.client.core.network.LinkletterNetwork

internal class DefaultRssUrlDataSource(
    private val network: LinkletterNetwork,
) : RssUrlDataSource {
    private val rssUrlCache = HashMap<String, String?>()

    override suspend fun getRssUrl(url: String): String? {
        val key = url.trim().removeSuffix("/")
        rssUrlCache[key]?.let { return it }

        val velogRssUrl = getVelogRssUrlOrNull(key)
        if (velogRssUrl != null) {
            rssUrlCache[key] = velogRssUrl
            return velogRssUrl
        }

        val html = network.fetchContent(url)
        val rssUrl = RssUrlParser.findRssUrl(html, url) ?: findFallbackRssUrl(url)

        rssUrlCache[key] = rssUrl
        return rssUrl
    }

    private fun getVelogRssUrlOrNull(url: String): String? {
        val match = Regex("""https?://velog\.io/@([^/]+)""").find(url)?.groupValues?.getOrNull(1)
        return match?.let { "https://v2.velog.io/rss/@$it" }
    }

    private suspend fun findFallbackRssUrl(baseUrl: String): String? {
        val fallbackPaths = listOf("/feed.xml", "/rss.xml", "/rss", "/feed", "/atom.xml")
        for (path in fallbackPaths) {
            val fullUrl = baseUrl.trimEnd('/') + path
            if (network.checkUrlExists(fullUrl)) return fullUrl
        }
        return null
    }
}

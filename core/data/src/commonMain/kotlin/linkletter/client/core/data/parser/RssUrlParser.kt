package linkletter.client.core.data.parser

import com.fleeksoft.ksoup.Ksoup

internal object RssUrlParser {
    fun findRssUrl(
        html: String,
        baseUrl: String,
    ): String? {
        val document = Ksoup.parse(html, baseUrl)

        document
            .selectFirst(META_RSS_SELECTOR)
            ?.attr("abs:href")
            ?.takeIf { it.isNotBlank() }
            ?.let { return it }

        document
            .select(ANCHOR_RSS_SELECTOR)
            .mapNotNull { it.attr("abs:href") }
            .firstOrNull { it.isNotBlank() }
            ?.let { return it }

        return null
    }

    private const val META_RSS_SELECTOR = "link[rel=alternate][type~=rss|atom]"
    private const val ANCHOR_RSS_SELECTOR = "a[href$=.xml], a[href$=.rss]"
}

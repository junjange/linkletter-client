package linkletter.client.core.data.parser

import linkletter.client.core.model.BlogPlatform

internal object RssParserFactory {
    fun getParserFor(platform: BlogPlatform): RssParser =
        when (platform) {
            is BlogPlatform.Tistory -> TistoryRssParser
            is BlogPlatform.Velog -> VelogRssParser
            is BlogPlatform.Naver -> NaverRssParser
            is BlogPlatform.Brunch -> BrunchRssParser
            is BlogPlatform.Blog -> BlogRssParser
        }
}

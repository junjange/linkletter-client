package linkletter.client.core.data.parser

import com.fleeksoft.ksoup.Ksoup
import linkletter.client.core.model.Author
import linkletter.client.core.model.Blog
import linkletter.client.core.model.Post

internal object UnKnownBlogRssParser : RssParser {
    override fun parse(xml: String): Blog {
        val doc = Ksoup.parseXml(xml)
        val channel = doc.selectFirst(TAG_CHANNEL)
        val url = channel?.safeText(TAG_LINK).orEmpty()

        val name =
            channel
                ?.safeText(TAG_TITLE)
                ?.takeIf { it.isNotBlank() }
                ?: url

        val postList =
            doc.select(TAG_ITEM).mapNotNull { item ->
                val title = item.unescapedText(TAG_TITLE)
                val link = item.safeText(TAG_LINK)
                val pubDate = item.safeText(TAG_PUB_DATE)

                val descriptionHtml = item.unescapedText(TAG_DESCRIPTION)
                val innerHtml = Ksoup.parse(descriptionHtml)

                val imgUrl = innerHtml.selectFirst(TAG_IMG)?.attr(TAG_SRC)
                val thumbnailUrl = buildImageUrl(url, imgUrl)
                val description = innerHtml.text()

                Post(
                    title = title,
                    description = description,
                    link = link,
                    thumbnailUrl = thumbnailUrl,
                    pubDate = pubDate,
                )
            }

        return Blog(
            name = name,
            author = Author(name = name, imageUrl = null),
            url = url,
            postList = postList,
        )
    }

    private fun buildImageUrl(
        domain: String,
        imgUrl: String?,
    ): String? {
        if (imgUrl.isNullOrBlank()) return null

        val safeImgUrl = imgUrl.trim()
        return when {
            safeImgUrl.startsWith("http") ->
                safeImgUrl.replaceFirst("http://", "https://")

            domain.isNotBlank() -> {
                val base = normalizeDomain(domain)
                val cleanedImgUrl = safeImgUrl.replace("//", "/").trimStart('/')
                "$base/$cleanedImgUrl"
            }

            else -> null
        }
    }

    private fun normalizeDomain(domain: String): String =
        domain
            .replaceFirst("http://", "https://")
            .let { if (!it.startsWith("https://")) "https://$it" else it }
            .trimEnd('/')

    private const val TAG_CHANNEL = "channel"
    private const val TAG_ITEM = "item"
    private const val TAG_TITLE = "title"
    private const val TAG_LINK = "link"
    private const val TAG_PUB_DATE = "pubDate"
    private const val TAG_DESCRIPTION = "description"
    private const val TAG_IMG = "img"
    private const val TAG_SRC = "src"
}

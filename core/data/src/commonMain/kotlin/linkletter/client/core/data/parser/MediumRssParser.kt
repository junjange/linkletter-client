package linkletter.client.core.data.parser

import com.fleeksoft.ksoup.Ksoup
import linkletter.client.core.model.Author
import linkletter.client.core.model.Blog
import linkletter.client.core.model.Post

internal object MediumRssParser : RssParser {
    override fun parse(xml: String): Blog {
        val doc = Ksoup.parseXml(xml)
        val channel = doc.selectFirst(TAG_CHANNEL)
        var name = ""
        val imageUrl = channel?.safeText(TAG_IMAGE_URL)
        val url = channel?.safeText(TAG_IMAGE_LINK).orEmpty()

        val postList =
            doc.select(TAG_ITEM).mapNotNull { item ->
                val title = item.safeText(TAG_TITLE)
                val link = item.safeText(TAG_LINK)
                name = item.safeText(TAG_CREATOR)
                val pubDate = item.safeText(TAG_PUB_DATE)

                val descriptionHtml = item.unescapedText(TAG_ENCODED)
                val innerHtml = Ksoup.parseBodyFragment(descriptionHtml)
                val thumbnail = innerHtml.safeAttr(TAG_IMAGE, TAG_SRC)
                val description = innerHtml.body().text().trim()

                Post(
                    title = title,
                    author = Author(name = name, imageUrl = imageUrl),
                    description = description,
                    link = link,
                    thumbnailUrl = thumbnail,
                    pubDate = pubDate,
                )
            }

        return Blog(
            name = name,
            author = Author(name = name, imageUrl = imageUrl),
            url = url,
            postList = postList,
        )
    }

    private const val TAG_CHANNEL = "channel"
    private const val TAG_ITEM = "item"
    private const val TAG_TITLE = "title"
    private const val TAG_LINK = "link"
    private const val TAG_PUB_DATE = "pubDate"
    private const val TAG_IMAGE = "img"
    private const val TAG_IMAGE_URL = "image > url"
    private const val TAG_IMAGE_LINK = "image > link"
    private const val TAG_SRC = "src"
    private const val TAG_CREATOR = "dc\\:creator"
    private const val TAG_ENCODED = "content\\:encoded"
}

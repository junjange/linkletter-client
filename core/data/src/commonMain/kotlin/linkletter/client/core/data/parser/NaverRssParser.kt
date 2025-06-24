package linkletter.client.core.data.parser

import com.fleeksoft.ksoup.Ksoup
import linkletter.client.core.model.Author
import linkletter.client.core.model.Post

internal object NaverRssParser : RssParser {
    override fun parse(xml: String): List<Post> {
        val doc = Ksoup.parseXml(xml)

        return doc.select(TAG_ITEM).mapNotNull { item ->
            val name = item.safeText(TAG_AUTHOR)
            val title = item.unescapedText(TAG_TITLE)
            val link = item.safeText(TAG_LINK)
            val pubDate = item.safeText(TAG_PUB_DATE)

            val descriptionHtml = item.unescapedText(TAG_DESCRIPTION)
            val innerHtml = Ksoup.parse(descriptionHtml)

            val thumbnail = innerHtml.safeAttr(TAG_IMAGE, TAG_SRC)
            val description = innerHtml.text()

            Post(
                author = Author(name = name, imageUrl = null),
                title = title,
                description = description,
                link = link,
                thumbnailUrl = thumbnail,
                pubDate = pubDate,
            )
        }
    }

    private const val TAG_ITEM = "item"
    private const val TAG_AUTHOR = "author"
    private const val TAG_TITLE = "title"
    private const val TAG_LINK = "link"
    private const val TAG_PUB_DATE = "pubDate"
    private const val TAG_DESCRIPTION = "description"
    private const val TAG_IMAGE = "img"
    private const val TAG_SRC = "src"
}

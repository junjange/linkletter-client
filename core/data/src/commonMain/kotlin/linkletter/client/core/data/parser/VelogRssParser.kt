package linkletter.client.core.data.parser

import com.fleeksoft.ksoup.Ksoup
import linkletter.client.core.model.Author
import linkletter.client.core.model.Post

internal object VelogRssParser : RssParser {
    override fun parse(xml: String): List<Post> {
        val doc = Ksoup.parseXml(xml)
        val channel = doc.selectFirst(TAG_CHANNEL)

        val authorName = channel.safeText(TAG_IMAGE_TITLE)
        val authorImageUrl = channel.safeText(TAG_IMAGE_URL)

        return doc.select(TAG_ITEM).mapNotNull { item ->
            val title = item.unescapedText(TAG_TITLE)
            val descriptionHtml = item.unescapedText(TAG_DESCRIPTION)

            val descriptionDoc = Ksoup.parse(descriptionHtml)
            val description = descriptionDoc.text()
            val thumbnail = descriptionDoc.safeAttr(TAG_IMAGE, TAG_SRC)

            Post(
                author = Author(name = authorName, imageUrl = authorImageUrl),
                title = title,
                description = description,
                link = item.safeText(TAG_LINK),
                thumbnailUrl = thumbnail,
                pubDate = item.safeText(TAG_PUB_DATE),
            )
        }
    }

    private const val TAG_CHANNEL = "channel"
    private const val TAG_ITEM = "item"
    private const val TAG_TITLE = "title"
    private const val TAG_LINK = "link"
    private const val TAG_PUB_DATE = "pubDate"
    private const val TAG_DESCRIPTION = "description"

    private const val TAG_IMAGE = "img"
    private const val TAG_SRC = "src"
    private const val TAG_IMAGE_TITLE = "image > title"
    private const val TAG_IMAGE_URL = "image > url"
}

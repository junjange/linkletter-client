package linkletter.client.core.data.parser

import com.fleeksoft.ksoup.Ksoup
import linkletter.client.core.model.Author
import linkletter.client.core.model.Blog
import linkletter.client.core.model.Post

internal object VelogRssParser : RssParser {
    override fun parse(xml: String): Blog {
        val doc = Ksoup.parseXml(xml)
        val channel = doc.selectFirst(TAG_CHANNEL)

        val name = channel.safeText(TAG_IMAGE_TITLE)
        val url = channel.safeText(TAG_LINK)
        val authorImageUrl = channel.safeText(TAG_IMAGE_URL)

        val postList =
            doc.select(TAG_ITEM).mapNotNull { item ->
                val title = item.unescapedText(TAG_TITLE)
                val descriptionHtml = item.unescapedText(TAG_DESCRIPTION)

                val descriptionDoc = Ksoup.parse(descriptionHtml)
                val description = descriptionDoc.text()
                val thumbnail = descriptionDoc.safeAttr(TAG_IMAGE, TAG_SRC)

                Post(
                    author = Author(name = name, imageUrl = authorImageUrl),
                    title = title,
                    description = description,
                    link = item.safeText(TAG_LINK),
                    thumbnailUrl = thumbnail,
                    pubDate = item.safeText(TAG_PUB_DATE),
                )
            }

        return Blog(
            name = name,
            author = Author(name = name, imageUrl = authorImageUrl),
            url = url,
            postList = postList,
        )
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

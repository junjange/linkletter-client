package linkletter.client.core.data.parser

import com.fleeksoft.ksoup.Ksoup
import linkletter.client.core.model.Author
import linkletter.client.core.model.Blog
import linkletter.client.core.model.Post

internal object TistoryRssParser : RssParser {
    override fun parse(xml: String): Blog {
        val doc = Ksoup.parseXml(xml)
        val channel = doc.selectFirst(TAG_CHANNEL)

        val name = channel.safeText(TAG_TITLE)
        val url = channel.safeText(TAG_LINK)
        val authorName = channel.safeText(TAG_EDITOR)
        val imageUrl = channel.safeText(TAG_IMAGE_URL)

        val postList =
            doc.select(TAG_ITEM).mapNotNull { item ->
                val title = item.unescapedText(TAG_TITLE)
                val link = item.safeText(TAG_LINK)
                val pubDate = item.safeText(TAG_PUB_DATE)

                val descriptionHtml = item.unescapedText(TAG_DESCRIPTION)
                val descriptionDoc = Ksoup.parse(descriptionHtml)

                val thumbnail = descriptionDoc.safeAttr(TAG_IMAGE, TAG_SRC)
                val description = descriptionDoc.text()

                Post(
                    author = Author(name = authorName, imageUrl = imageUrl),
                    title = title,
                    description = description,
                    link = link,
                    thumbnailUrl = thumbnail,
                    pubDate = pubDate,
                )
            }

        return Blog(
            name = name,
            author = Author(name = authorName, imageUrl = imageUrl),
            url = url,
            postList = postList,
        )
    }

    private const val TAG_ITEM = "item"
    private const val TAG_TITLE = "title"
    private const val TAG_LINK = "link"
    private const val TAG_PUB_DATE = "pubDate"
    private const val TAG_DESCRIPTION = "description"

    private const val TAG_CHANNEL = "channel"
    private const val TAG_EDITOR = "managingEditor"
    private const val TAG_IMAGE_URL = "image > url"
    private const val TAG_IMAGE = "img"
    private const val TAG_SRC = "src"
}

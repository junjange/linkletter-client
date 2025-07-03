package linkletter.client.core.data.parser

import com.fleeksoft.ksoup.nodes.Element
import linkletter.client.core.model.Blog

sealed interface RssParser {
    fun parse(xml: String): Blog
}

fun Element?.safeText(selector: String): String = this?.selectFirst(selector)?.text().orEmpty()

fun Element?.unescapedText(selector: String): String = this.safeText(selector).unescapeEntities()

fun Element?.safeAttr(
    selector: String,
    attr: String,
): String? = this?.selectFirst(selector)?.attr(attr)

private fun String.unescapeEntities(): String =
    this
        .replace("&lt;", "<")
        .replace("&gt;", ">")
        .replace("&amp;", "&")
        .replace("&quot;", "\"")
        .replace("&apos;", "'")
        .replace("&nbsp;", " ")
        .replace("&copy;", "©")
        .replace("&reg;", "®")
        .replace("&trade;", "™")
        .replace("&rarr;", "→")
        .replace("&larr;", "←")
        .replace("&uarr;", "↑")
        .replace("&darr;", "↓")
        .replace("&plusmn;", "±")
        .replace("&times;", "×")
        .replace("&divide;", "÷")
        .replace("&ne;", "≠")
        .replace("&le;", "≤")
        .replace("&ge;", "≥")
        .replace("&lsquo;", "‘")
        .replace("&rsquo;", "’")
        .replace("&ldquo;", "“")
        .replace("&rdquo;", "”")
        .replace("&ndash;", "–")
        .replace("&mdash;", "—")
        .replace("&yen;", "¥")
        .replace("&euro;", "€")
        .replace("&pound;", "£")
        .replace("&cent;", "¢")
        .replace("&hellip;", "…")
        .replace("&middot;", "·")
        .replace("&bull;", "•")
        .replace("&sect;", "§")
        .replace("&deg;", "°")

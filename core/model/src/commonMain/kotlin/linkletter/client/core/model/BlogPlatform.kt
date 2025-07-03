package linkletter.client.core.model

sealed class BlogPlatform {
    abstract val rssUrl: String

    data class Tistory(
        override val rssUrl: String,
    ) : BlogPlatform()

    data class Velog(
        override val rssUrl: String,
    ) : BlogPlatform()

    data class Naver(
        override val rssUrl: String,
    ) : BlogPlatform()

    data class Brunch(
        override val rssUrl: String,
    ) : BlogPlatform()

    data class UnKnown(
        override val rssUrl: String,
    ) : BlogPlatform()

    companion object {
        fun from(rssUrl: String): BlogPlatform {
            val cleaned = rssUrl.trim()

            return when {
                "tistory.com" in cleaned -> Tistory(rssUrl = rssUrl)

                "velog.io" in cleaned -> Velog(rssUrl = rssUrl)

                "naver.com" in cleaned -> Naver(rssUrl = rssUrl)

                "brunch.co.kr" in cleaned -> Brunch(rssUrl = rssUrl)

                else -> UnKnown(rssUrl = rssUrl)
            }
        }
    }
}

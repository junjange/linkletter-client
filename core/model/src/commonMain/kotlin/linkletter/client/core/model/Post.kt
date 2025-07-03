package linkletter.client.core.model

data class Post(
    val author: Author,
    val title: String,
    val description: String,
    val link: String,
    val thumbnailUrl: String?,
    val pubDate: String,
) {
    companion object {
        val Default =
            Post(
                author = Author.Default,
                title = "",
                description = "",
                link = "",
                thumbnailUrl = "",
                pubDate = "",
            )
    }
}

package linkletter.client.core.model

data class Blog(
    val name: String,
    val author: Author,
    val url: String,
    val postList: List<Post>,
) {
    companion object {
        val Default =
            Blog(
                name = "",
                author = Author.Default,
                url = "",
                postList =
                    List(5) {
                        Post.Default
                    },
            )
    }
}

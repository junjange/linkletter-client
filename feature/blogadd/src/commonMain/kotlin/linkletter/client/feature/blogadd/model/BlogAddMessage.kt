package linkletter.client.feature.blogadd.model

sealed class BlogAddMessage {
    abstract val value: String

    data class BlogFollowed(
        override val value: String = "${blog.name}를 팔로우했습니다.",
        val blog: Blog,
    ) : BlogAddMessage()
}

package linkletter.client.feature.blogfollow.model

sealed class BlogFollowMessage {
    abstract val value: String

    data class BlogFollowed(
        val blogName: String,
        override val value: String = "${blogName}를 팔로우했습니다.",
    ) : BlogFollowMessage()
}

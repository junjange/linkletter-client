package linkletter.client.feature.blogfollow.model

sealed class BlogFollowMessage {
    abstract val value: String

    data class BlogFollowed(
        val blogName: String,
        override val value: String = "${blogName}를 팔로우했습니다.",
    ) : BlogFollowMessage()

    data class BlogUnfollowed(
        val blogName: String,
        override val value: String = "${blogName}를 언팔로우했습니다.",
    ) : BlogFollowMessage()

    data object BlogNotFound : BlogFollowMessage() {
        override val value: String = "블로그를 찾을 수 없습니다. URL을 확인해주세요."
    }
}

package linkletter.client.feature.addblog.model

sealed class AddBlogMessage {
    abstract val value: String

    data class Followed(
        val blogName: String,
        override val value: String = "${blogName}를 팔로우했습니다.",
    ) : AddBlogMessage()

    data class Unfollowed(
        val blogName: String,
        override val value: String = "${blogName}를 언팔로우했습니다.",
    ) : AddBlogMessage()

    data object NotFound : AddBlogMessage() {
        override val value: String = "블로그를 찾을 수 없습니다. URL을 확인해주세요."
    }
}

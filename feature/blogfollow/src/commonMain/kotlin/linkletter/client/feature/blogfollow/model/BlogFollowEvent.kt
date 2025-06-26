package linkletter.client.feature.blogfollow.model

sealed interface BlogFollowEvent {
    object BackClicked : BlogFollowEvent

    data class BlogClicked(
        val link: String,
    ) : BlogFollowEvent

    data class BlogFollowToggleClicked(
        val blogFollowUiModel: BlogFollowUiModel,
    ) : BlogFollowEvent
}

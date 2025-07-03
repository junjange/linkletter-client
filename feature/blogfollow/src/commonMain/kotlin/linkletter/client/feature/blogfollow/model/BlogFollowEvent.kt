package linkletter.client.feature.blogfollow.model

sealed interface BlogFollowEvent {
    data class Search(
        val query: String,
    ) : BlogFollowEvent

    data object BackClicked : BlogFollowEvent

    data class BlogClicked(
        val link: String,
    ) : BlogFollowEvent

    data object BlogFollowToggleClicked : BlogFollowEvent
}

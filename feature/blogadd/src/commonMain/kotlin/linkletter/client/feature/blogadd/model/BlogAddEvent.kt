package linkletter.client.feature.blogadd.model

sealed interface BlogAddEvent {
    object BackClicked : BlogAddEvent

    data class BlogClicked(
        val link: String,
    ) : BlogAddEvent

    data class BlogFollowToggleClicked(
        val blog: Blog,
    ) : BlogAddEvent
}

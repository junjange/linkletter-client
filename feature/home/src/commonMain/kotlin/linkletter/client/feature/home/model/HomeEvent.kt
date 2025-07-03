package linkletter.client.feature.home.model

sealed interface HomeEvent {
    data object FeedRefresh : HomeEvent

    data class PostClicked(
        val link: String,
    ) : HomeEvent

    data object AddBlogClicked : HomeEvent
}

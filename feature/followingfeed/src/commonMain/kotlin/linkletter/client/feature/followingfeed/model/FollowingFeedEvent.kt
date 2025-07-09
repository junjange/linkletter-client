package linkletter.client.feature.followingfeed.model

sealed interface FollowingFeedEvent {
    data object FeedRefresh : FollowingFeedEvent

    data class PostClicked(
        val link: String,
    ) : FollowingFeedEvent

    data object AddBlogClicked : FollowingFeedEvent

    data class QueryChanged(
        val query: String,
    ) : FollowingFeedEvent
}

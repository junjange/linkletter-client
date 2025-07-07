package linkletter.client.feature.followingfeed.model

sealed interface FollowingFeedEffect {
    data class OpenUri(
        val link: String,
    ) : FollowingFeedEffect

    data object NavigateToAddBlog : FollowingFeedEffect
}

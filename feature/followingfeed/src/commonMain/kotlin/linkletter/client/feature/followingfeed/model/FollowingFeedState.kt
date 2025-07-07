package linkletter.client.feature.followingfeed.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import linkletter.client.core.model.Post

@Stable
sealed interface FollowingFeedState {
    @Immutable
    data object Loading : FollowingFeedState

    @Immutable
    data object Empty : FollowingFeedState

    @Immutable
    data class Feed(
        val postList: List<Post>,
    ) : FollowingFeedState
}

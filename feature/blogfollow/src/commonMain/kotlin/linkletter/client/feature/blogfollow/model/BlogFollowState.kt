package linkletter.client.feature.blogfollow.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import linkletter.client.core.model.Blog

@Stable
sealed interface BlogFollowState {
    @Immutable
    data object Loading : BlogFollowState

    @Immutable
    data object Empty : BlogFollowState

    @Immutable
    data class BlogFollow(
        val blog: Blog,
        val isFollowed: Boolean,
    ) : BlogFollowState {
        companion object {
            val Default =
                BlogFollow(
                    blog = Blog.Default,
                    isFollowed = false,
                )
        }
    }
}

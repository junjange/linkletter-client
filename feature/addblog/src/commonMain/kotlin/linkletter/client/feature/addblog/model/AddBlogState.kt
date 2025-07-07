package linkletter.client.feature.addblog.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import linkletter.client.core.model.Blog

@Stable
sealed interface AddBlogState {
    @Immutable
    data object Loading : AddBlogState

    @Immutable
    data object Empty : AddBlogState

    @Immutable
    data class AddBlog(
        val blog: Blog,
        val isFollowed: Boolean,
    ) : AddBlogState {
        companion object {
            val Default =
                AddBlog(
                    blog = Blog.Default,
                    isFollowed = false,
                )
        }
    }
}

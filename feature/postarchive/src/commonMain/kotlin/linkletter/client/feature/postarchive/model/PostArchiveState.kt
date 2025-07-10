package linkletter.client.feature.postarchive.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import linkletter.client.core.model.Post

@Stable
sealed interface PostArchiveState {
    @Immutable
    data object Loading : PostArchiveState

    @Immutable
    data object Empty : PostArchiveState

    @Immutable
    data class Archive(
        val postList: List<Post>,
    ) : PostArchiveState
}

package linkletter.client.feature.home.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import linkletter.client.core.model.Blog

@Stable
sealed interface HomeState {
    @Immutable
    data object Loading : HomeState

    @Immutable
    data object Empty : HomeState

    @Immutable
    data class Feed(
        val blog: Blog,
    ) : HomeState
}

package linkletter.client.feature.home.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import linkletter.client.core.model.Post
import linkletter.client.feature.home.model.HomeState

@Composable
internal fun PostList(
    state: HomeState,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
    onPostClick: (link: String) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = lazyListState,
    ) {
        when (state) {
            is HomeState.Loading -> {
                items(SHIMMERING_ITEM_COUNT) {
                    PostCard(
                        post = Post.Default,
                        showPlaceholder = true,
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )
                }
            }

            is HomeState.Feed -> {
                items(state.posts.size) { index ->
                    PostCard(
                        post = state.posts[index],
                        showPlaceholder = false,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        onClick = onPostClick,
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.padding(bottom = 32.dp))
        }
    }
}

private const val SHIMMERING_ITEM_COUNT = 8

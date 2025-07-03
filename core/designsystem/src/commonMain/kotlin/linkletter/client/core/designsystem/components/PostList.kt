package linkletter.client.core.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import linkletter.client.core.model.Author
import linkletter.client.core.model.Post

@Composable
fun PostList(
    posts: List<Post>,
    author: Author,
    showPlaceholder: Boolean,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
    onPostClick: (link: String) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = lazyListState,
    ) {
        items(posts.size) { index ->
            PostCard(
                post = posts[index],
                author = author,
                showPlaceholder = showPlaceholder,
                modifier = Modifier.padding(horizontal = 16.dp),
                onClick = onPostClick,
            )
        }

        item {
            Spacer(modifier = Modifier.padding(bottom = 32.dp))
        }
    }
}

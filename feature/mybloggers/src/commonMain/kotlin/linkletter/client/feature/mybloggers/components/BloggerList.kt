package linkletter.client.feature.mybloggers.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import linkletter.client.feature.mybloggers.model.Blogger

@Composable
fun BloggerList(
    bloggers: List<Blogger>,
    showPlaceholder: Boolean,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
    onBlogClick: (link: String) -> Unit,
    toggleBlogger: (blogger: Blogger) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = lazyListState,
    ) {
        items(bloggers.size) { index ->
            BloggerCard(
                blogger = bloggers[index],
                showPlaceholder = showPlaceholder,
                onBlogClick = onBlogClick,
                toggleBlogger = toggleBlogger,
            )
        }

        item {
            Spacer(modifier = Modifier.padding(bottom = 32.dp))
        }
    }
}

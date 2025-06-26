package linkletter.client.feature.blogadd

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import linkletter.client.core.designsystem.theme.LinkletterTheme
import linkletter.client.core.designsystem.utils.addFocusCleaner
import linkletter.client.feature.blogadd.components.BlogAddSearchBar
import linkletter.client.feature.blogadd.components.BlogListItem
import linkletter.client.feature.blogadd.model.Blog
import linkletter.client.feature.blogadd.model.BlogAddEffect
import linkletter.client.feature.blogadd.model.BlogAddEvent
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogAddScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BlogAddViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current
    val uriHandler = LocalUriHandler.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is BlogAddEffect.NavigateBack -> onBackClick()
                is BlogAddEffect.OpenUri -> uriHandler.openUri(effect.link)
            }
        }
    }

    Scaffold(
        containerColor = LinkletterTheme.colorScheme.background,
        modifier =
            modifier
                .systemBarsPadding()
                .fillMaxSize()
                .addFocusCleaner(focusManager),
        topBar = {
            BlogAddSearchBar(
                modifier = Modifier.padding(all = 8.dp),
                focusManager = focusManager,
                onBackClick = {
                    viewModel.onEvent(event = BlogAddEvent.BackClicked)
                    focusManager.clearFocus()
                },
                onSearch = { query ->
                    // 검색 기능 미구현 부분
                },
            )
        },
    ) { innerPadding ->
        BlogAddContent(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .addFocusCleaner(focusManager),
            blogList = state.blogList,
            onBlogClick = { blog ->
                viewModel.onEvent(event = BlogAddEvent.BlogClicked(link = blog.link))
                focusManager.clearFocus()
            },
            onBlogFollow = { blog ->
                viewModel.onEvent(event = BlogAddEvent.BlogFollowToggleClicked(blog = blog))
            },
        )
    }
}

@Composable
private fun BlogAddContent(
    modifier: Modifier = Modifier,
    blogList: List<Blog>,
    onBlogClick: (Blog) -> Unit,
    onBlogFollow: (Blog) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(blogList.size) { index ->
            BlogListItem(
                blog = blogList[index],
                onClick = onBlogClick,
                onFollow = onBlogFollow,
            )
        }
    }
}

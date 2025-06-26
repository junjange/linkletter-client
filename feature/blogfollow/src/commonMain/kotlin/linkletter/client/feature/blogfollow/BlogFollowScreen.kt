package linkletter.client.feature.blogfollow

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import linkletter.client.core.designsystem.theme.LinkletterTheme
import linkletter.client.core.designsystem.utils.addFocusCleaner
import linkletter.client.feature.blogfollow.components.BlogFollowListItem
import linkletter.client.feature.blogfollow.components.BlogFollowSearchBar
import linkletter.client.feature.blogfollow.model.BlogFollowEffect
import linkletter.client.feature.blogfollow.model.BlogFollowEvent
import linkletter.client.feature.blogfollow.model.BlogFollowUiModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogFollowScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BlogFollowViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current
    val uriHandler = LocalUriHandler.current
    val imeInsets = WindowInsets.ime

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is BlogFollowEffect.NavigateBack -> onBackClick()
                is BlogFollowEffect.OpenUri -> uriHandler.openUri(effect.link)
                is BlogFollowEffect.ShowMessage -> {
                    snackbarHostState.showSnackbar(
                        message = effect.message.value,
                        duration = SnackbarDuration.Short,
                    )
                }
            }
        }
    }

    Scaffold(
        containerColor = LinkletterTheme.colorScheme.background,
        contentWindowInsets = WindowInsets.systemBars,
        modifier =
            modifier
                .systemBarsPadding()
                .fillMaxSize()
                .addFocusCleaner(focusManager),
        topBar = {
            BlogFollowSearchBar(
                modifier = Modifier.padding(all = 8.dp),
                focusManager = focusManager,
                onBackClick = {
                    viewModel.onEvent(event = BlogFollowEvent.BackClicked)
                    focusManager.clearFocus()
                },
                onSearch = { query ->
                    // 검색 기능 미구현 부분
                },
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier =
                    Modifier.padding(
                        paddingValues = imeInsets.asPaddingValues(),
                    ),
            )
        },
    ) { innerPadding ->
        BlogFollowContent(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .addFocusCleaner(focusManager),
            blogFollowUiModelList = state.blogFollowUiModelList,
            onBlogClick = { blog ->
                viewModel.onEvent(event = BlogFollowEvent.BlogClicked(link = blog.link))
                focusManager.clearFocus()
            },
            onBlogFollow = { blog ->
                viewModel.onEvent(event = BlogFollowEvent.BlogFollowToggleClicked(blogFollowUiModel = blog))
                focusManager.clearFocus()
            },
        )
    }
}

@Composable
private fun BlogFollowContent(
    modifier: Modifier = Modifier,
    blogFollowUiModelList: List<BlogFollowUiModel>,
    onBlogClick: (BlogFollowUiModel) -> Unit,
    onBlogFollow: (BlogFollowUiModel) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(blogFollowUiModelList.size) { index ->
            BlogFollowListItem(
                blogFollowUiModel = blogFollowUiModelList[index],
                onClick = onBlogClick,
                onFollow = onBlogFollow,
            )
        }
    }
}

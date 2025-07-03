package linkletter.client.feature.blogfollow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
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
import linkletter.client.core.designsystem.components.EmptyScreen
import linkletter.client.core.designsystem.components.PostList
import linkletter.client.core.designsystem.theme.LinkletterTheme
import linkletter.client.core.designsystem.utils.addFocusCleaner
import linkletter.client.feature.blogfollow.components.BlogFollowSearchBar
import linkletter.client.feature.blogfollow.components.BlogResultCard
import linkletter.client.feature.blogfollow.model.BlogFollowEffect
import linkletter.client.feature.blogfollow.model.BlogFollowEvent
import linkletter.client.feature.blogfollow.model.BlogFollowState.BlogFollow
import linkletter.client.feature.blogfollow.model.BlogFollowState.Empty
import linkletter.client.feature.blogfollow.model.BlogFollowState.Loading
import linkletter_client.feature.blogfollow.generated.resources.Res
import linkletter_client.feature.blogfollow.generated.resources.empty_subtitle
import linkletter_client.feature.blogfollow.generated.resources.empty_title
import org.jetbrains.compose.resources.stringResource
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
                    viewModel.onEvent(event = BlogFollowEvent.Search(query = query))
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
        when (state) {
            is Loading -> {
                BlogFollowContent(
                    blogFollow = BlogFollow.Default,
                    showPlaceholder = true,
                    modifier =
                        Modifier
                            .padding(innerPadding)
                            .addFocusCleaner(focusManager),
                    onBlogClick = {},
                    onBlogFollow = {},
                )
            }

            is BlogFollow -> {
                BlogFollowContent(
                    blogFollow = state as BlogFollow,
                    showPlaceholder = false,
                    modifier =
                        Modifier
                            .padding(innerPadding)
                            .addFocusCleaner(focusManager),
                    onBlogClick = { blogUrl ->
                        viewModel.onEvent(event = BlogFollowEvent.BlogClicked(link = blogUrl))
                        focusManager.clearFocus()
                    },
                    onBlogFollow = {
                        viewModel.onEvent(event = BlogFollowEvent.BlogFollowToggleClicked)
                        focusManager.clearFocus()
                    },
                )
            }

            is Empty -> {
                EmptyScreen(
                    title = stringResource(Res.string.empty_title),
                    subTitle = stringResource(Res.string.empty_subtitle),
                )
            }
        }
    }
}

@Composable
private fun BlogFollowContent(
    blogFollow: BlogFollow,
    showPlaceholder: Boolean,
    modifier: Modifier = Modifier,
    onBlogClick: (String) -> Unit,
    onBlogFollow: () -> Unit,
) {
    val lazyListState = rememberLazyListState()

    Column(
        modifier =
            modifier.fillMaxSize(),
    ) {
        BlogResultCard(
            blogFollow = blogFollow,
            showPlaceholder = showPlaceholder,
            onBlogClick = onBlogClick,
            onBlogFollow = onBlogFollow,
        )

        Spacer(modifier = Modifier.height(16.dp))

        PostList(
            posts = blogFollow.blog.postList,
            author = blogFollow.blog.author,
            showPlaceholder = showPlaceholder,
            lazyListState = lazyListState,
            onPostClick = onBlogClick,
        )
    }
}

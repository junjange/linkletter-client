package linkletter.client.feature.followingfeed

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import linkletter.client.core.designsystem.components.EmptyScreen
import linkletter.client.core.designsystem.components.PostList
import linkletter.client.core.designsystem.theme.LinkletterTheme
import linkletter.client.core.designsystem.utils.addFocusCleaner
import linkletter.client.core.model.Blog
import linkletter.client.feature.followingfeed.components.ExpandableActionButton
import linkletter.client.feature.followingfeed.components.FollowingFeedSearchBar
import linkletter.client.feature.followingfeed.model.FollowingFeedEffect
import linkletter.client.feature.followingfeed.model.FollowingFeedEvent
import linkletter.client.feature.followingfeed.model.FollowingFeedState
import linkletter_client.feature.followingfeed.generated.resources.Res
import linkletter_client.feature.followingfeed.generated.resources.empty_subtitle
import linkletter_client.feature.followingfeed.generated.resources.empty_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowingFeedScreen(
    modifier: Modifier = Modifier,
    viewModel: FollowingFeedViewModel = koinViewModel(),
    onBlogFollowClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current
    val uriHandler = LocalUriHandler.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is FollowingFeedEffect.OpenUri -> uriHandler.openUri(effect.link)
                is FollowingFeedEffect.NavigateToAddBlog -> onBlogFollowClick()
            }
        }
    }

    val lazyListState = rememberLazyListState()
    val firstVisibleItemScrollOffset =
        remember { derivedStateOf { lazyListState.firstVisibleItemScrollOffset } }

    Scaffold(
        containerColor = LinkletterTheme.colorScheme.background,
        modifier =
            Modifier
                .systemBarsPadding()
                .fillMaxSize()
                .padding(bottom = 80.dp),
        topBar = {
            FollowingFeedSearchBar(
                modifier = Modifier.padding(all = 16.dp),
                focusManager = focusManager,
                onSearch = { query ->
                    // 검색 기능 미구현 부분
                },
            )
        },
        floatingActionButton = {
            ExpandableActionButton(
                isFabExpanded = firstVisibleItemScrollOffset.value == 0,
                onClick = { viewModel.onEvent(FollowingFeedEvent.AddBlogClicked) },
            )
        },
    ) { innerPadding ->
        FollowingFeedContent(
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .addFocusCleaner(focusManager),
            state = state,
            lazyListState = lazyListState,
            onRefresh = { viewModel.onEvent(FollowingFeedEvent.FeedRefresh) },
            onPostClick = { link -> viewModel.onEvent(FollowingFeedEvent.PostClicked(link = link)) },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FollowingFeedContent(
    state: FollowingFeedState,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit,
    onPostClick: (link: String) -> Unit,
) {
    val refreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = state is FollowingFeedState.Loading,
        onRefresh = onRefresh,
        state = refreshState,
        modifier = modifier.fillMaxSize(),
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = state is FollowingFeedState.Loading,
                containerColor = LinkletterTheme.colorScheme.surface,
                color = LinkletterTheme.colorScheme.primary,
                state = refreshState,
            )
        },
    ) {
        when (state) {
            is FollowingFeedState.Loading -> {
                PostList(
                    posts = Blog.Default.postList,
                    showPlaceholder = true,
                    lazyListState = lazyListState,
                    onPostClick = onPostClick,
                )
            }

            is FollowingFeedState.Feed -> {
                PostList(
                    posts = state.postList,
                    showPlaceholder = false,
                    lazyListState = lazyListState,
                    onPostClick = onPostClick,
                )
            }

            is FollowingFeedState.Empty -> {
                EmptyScreen(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                    title = stringResource(Res.string.empty_title),
                    subTitle = stringResource(Res.string.empty_subtitle),
                )
            }
        }
    }
}

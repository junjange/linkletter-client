package linkletter.client.feature.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
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
import linkletter.client.core.designsystem.theme.LinkletterTheme
import linkletter.client.core.designsystem.utils.addFocusCleaner
import linkletter.client.feature.home.componets.ExpandableActionButton
import linkletter.client.feature.home.componets.HomeSearchBar
import linkletter.client.feature.home.componets.PostList
import linkletter.client.feature.home.model.HomeEffect
import linkletter.client.feature.home.model.HomeEvent
import linkletter.client.feature.home.model.HomeState
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    onBlogAddClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current
    val uriHandler = LocalUriHandler.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeEffect.OpenUri -> uriHandler.openUri(effect.link)
                is HomeEffect.NavigateToAddBlog -> onBlogAddClick()
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
            HomeSearchBar(
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
                onClick = { viewModel.onEvent(HomeEvent.AddBlogClicked) },
            )
        },
    ) { innerPadding ->
        HomeContent(
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .addFocusCleaner(focusManager),
            state = state,
            lazyListState = lazyListState,
            onPostClick = { link -> viewModel.onEvent(HomeEvent.PostClicked(link = link)) },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
    state: HomeState,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
    onPostClick: (link: String) -> Unit,
) {
    val refreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = state is HomeState.Loading,
        onRefresh = {
            // TODO 로직 추가
        },
        state = refreshState,
        modifier = modifier.fillMaxSize(),
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = state is HomeState.Loading,
                containerColor = LinkletterTheme.colorScheme.surface,
                color = LinkletterTheme.colorScheme.primary,
                state = refreshState,
            )
        },
    ) {
        PostList(
            state = state,
            lazyListState = lazyListState,
            onPostClick = onPostClick,
        )
    }
}

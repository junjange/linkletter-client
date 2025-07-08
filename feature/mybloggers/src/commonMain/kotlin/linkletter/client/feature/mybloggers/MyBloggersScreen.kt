package linkletter.client.feature.mybloggers

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import linkletter.client.core.designsystem.components.EmptyScreen
import linkletter.client.core.designsystem.components.LinkletterTopAppBar
import linkletter.client.core.designsystem.theme.LinkletterTheme
import linkletter.client.feature.mybloggers.components.BloggerList
import linkletter.client.feature.mybloggers.model.Blogger
import linkletter.client.feature.mybloggers.model.MyBloggersEffect
import linkletter.client.feature.mybloggers.model.MyBloggersEvent
import linkletter.client.feature.mybloggers.model.MyBloggersState
import linkletter_client.feature.mybloggers.generated.resources.Res
import linkletter_client.feature.mybloggers.generated.resources.empty_subtitle
import linkletter_client.feature.mybloggers.generated.resources.empty_title
import linkletter_client.feature.mybloggers.generated.resources.title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBloggersScreen(
    viewModel: MyBloggersViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val uriHandler = LocalUriHandler.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MyBloggersEffect.OpenUri -> uriHandler.openUri(effect.link)
            }
        }
    }

    Scaffold(
        containerColor = LinkletterTheme.colorScheme.background,
        modifier =
            modifier
                .systemBarsPadding()
                .fillMaxSize()
                .padding(bottom = 80.dp),
        topBar = {
            LinkletterTopAppBar(title = stringResource(Res.string.title))
        },
    ) { innerPadding ->
        MyBloggersContent(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            state = state,
            onRefresh = { viewModel.onEvent(MyBloggersEvent.Refresh) },
            onBloggerClick = { blogUrl ->
                viewModel.onEvent(event = MyBloggersEvent.BloggerClicked(link = blogUrl))
            },
            toggleBlogger = { blogger ->
                viewModel.onEvent(event = MyBloggersEvent.BloggerToggleClicked(blogger = blogger))
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyBloggersContent(
    state: MyBloggersState,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit,
    onBloggerClick: (String) -> Unit,
    toggleBlogger: (Blogger) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    val refreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = state is MyBloggersState.Loading,
        onRefresh = onRefresh,
        state = refreshState,
        modifier = modifier.fillMaxSize(),
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = state is MyBloggersState.Loading,
                containerColor = LinkletterTheme.colorScheme.surface,
                color = LinkletterTheme.colorScheme.primary,
                state = refreshState,
            )
        },
    ) {
        when (state) {
            is MyBloggersState.Loading -> {
                BloggerList(
                    bloggers = Blogger.Default,
                    showPlaceholder = true,
                    lazyListState = lazyListState,
                    onBlogClick = onBloggerClick,
                    toggleBlogger = toggleBlogger,
                )
            }

            is MyBloggersState.Loaded -> {
                BloggerList(
                    bloggers = state.bloggerList,
                    showPlaceholder = false,
                    lazyListState = lazyListState,
                    onBlogClick = onBloggerClick,
                    toggleBlogger = toggleBlogger,
                )
            }

            is MyBloggersState.Empty -> {
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

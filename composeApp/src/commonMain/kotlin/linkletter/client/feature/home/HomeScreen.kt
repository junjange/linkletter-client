package linkletter.client.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import linkletter.client.core.designsystem.utils.addFocusCleaner
import linkletter.client.feature.home.componets.HomeSearchBar
import linkletter.client.feature.home.componets.PostCard
import linkletter.client.feature.home.model.HomeEffect
import linkletter.client.feature.home.model.HomeEvent
import linkletter.client.feature.home.model.HomeState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current
    val uriHandler = LocalUriHandler.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeEffect.OpenUri -> uriHandler.openUri(effect.link)
            }
        }
    }

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .addFocusCleaner(focusManager),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HomeSearchBar(
            modifier = Modifier.padding(all = 16.dp),
            focusManager = focusManager,
            onSearch = { query ->
            },
        )
        PostList(
            state = state,
            onClick = { link -> viewModel.onEvent(HomeEvent.PostClicked(link = link)) },
        )
    }
}

@Composable
private fun PostList(
    state: HomeState,
    modifier: Modifier = Modifier,
    onClick: (link: String) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier,
    ) {
        when (state) {
            is HomeState.Loading -> {
                items(SHIMMERING_ITEM_COUNT) {
                    PostCard(
                        post = HomeState.Feed.Post.Default,
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
                        onClick = onClick,
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

package linkletter.client.feature.followingfeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import linkletter.client.core.common.formatRssDateToKorean
import linkletter.client.core.domain.usecase.FetchBlogListUseCase
import linkletter.client.feature.followingfeed.model.FollowingFeedEffect
import linkletter.client.feature.followingfeed.model.FollowingFeedEvent
import linkletter.client.feature.followingfeed.model.FollowingFeedState
import linkletter.client.feature.followingfeed.model.Trigger

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class FollowingFeedViewModel(
    private val fetchBlogListUseCase: FetchBlogListUseCase,
) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val refresh = MutableSharedFlow<Unit>()

    private val trigger: Flow<Trigger> =
        merge(
            _query
                .debounce(300)
                .distinctUntilChanged()
                .map { Trigger.Search(it) },
            refresh
                .map { Trigger.Refresh },
        )

    private val postsFlow: Flow<FollowingFeedState> =
        trigger
            .flatMapLatest { trig ->
                val queryForFetch =
                    when (trig) {
                        is Trigger.Search -> trig.query
                        is Trigger.Refresh -> _query.value
                    }

                val baseFlow: Flow<FollowingFeedState> =
                    fetchBlogListUseCase(queryForFetch)
                        .map { list ->
                            val posts =
                                list
                                    .map { it.copy(pubDate = it.pubDate.formatRssDateToKorean()) }
                                    .sortedByDescending { it.pubDate }

                            if (posts.isEmpty()) {
                                FollowingFeedState.Empty
                            } else {
                                FollowingFeedState.Feed(posts)
                            }
                        }

                if (trig is Trigger.Refresh) {
                    baseFlow.onStart { emit(FollowingFeedState.Loading) }
                } else {
                    baseFlow
                }
            }.onStart { emit(FollowingFeedState.Loading) }
            .catch { emit(FollowingFeedState.Empty) }

    val state: StateFlow<FollowingFeedState> =
        postsFlow
            .stateIn(viewModelScope, SharingStarted.Lazily, FollowingFeedState.Loading)

    private val _effect = Channel<FollowingFeedEffect>(Channel.BUFFERED)
    val effect get() = _effect.receiveAsFlow()

    fun onEvent(event: FollowingFeedEvent) {
        when (event) {
            is FollowingFeedEvent.FeedRefresh -> refresh()
            is FollowingFeedEvent.PostClicked -> openUri(link = event.link)
            is FollowingFeedEvent.AddBlogClicked -> navigateToAddBlog()
            is FollowingFeedEvent.QueryChanged -> onQueryChanged(newQuery = event.query)
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            refresh.emit(Unit)
        }
    }

    private fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
    }

    private fun navigateToAddBlog() {
        viewModelScope.launch {
            _effect.send(FollowingFeedEffect.NavigateToAddBlog)
        }
    }

    private fun openUri(link: String) {
        viewModelScope.launch {
            _effect.send(FollowingFeedEffect.OpenUri(link))
        }
    }
}

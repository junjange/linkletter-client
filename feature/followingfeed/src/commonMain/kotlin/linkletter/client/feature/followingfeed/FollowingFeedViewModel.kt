package linkletter.client.feature.followingfeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import linkletter.client.core.common.formatRssDateToKorean
import linkletter.client.core.domain.usecase.FetchBlogListUseCase
import linkletter.client.core.model.Post
import linkletter.client.feature.followingfeed.model.FollowingFeedEffect
import linkletter.client.feature.followingfeed.model.FollowingFeedEvent
import linkletter.client.feature.followingfeed.model.FollowingFeedState

class FollowingFeedViewModel(
    private val fetchBlogListUseCase: FetchBlogListUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<FollowingFeedState>(FollowingFeedState.Loading)
    val state = _state.asStateFlow()

    private val _effect = Channel<FollowingFeedEffect>(Channel.BUFFERED)
    val effect get() = _effect.receiveAsFlow()

    init {
        fetchFeed()
    }

    fun onEvent(event: FollowingFeedEvent) {
        when (event) {
            is FollowingFeedEvent.FeedRefresh -> fetchFeed()
            is FollowingFeedEvent.PostClicked -> openUri(event.link)
            is FollowingFeedEvent.AddBlogClicked -> navigateToAddBlog()
        }
    }

    private fun fetchFeed() {
        fetchBlogListUseCase()
            .onStart {
                _state.value = FollowingFeedState.Loading
                delay(300)
            }.onEach { blogList ->
                if (blogList.isEmpty()) {
                    _state.value = FollowingFeedState.Empty
                    return@onEach
                }
                val newPostList = mutableListOf<Post>()
                blogList.map { blog ->
                    newPostList +=
                        blog.postList.map { post ->
                            post.copy(pubDate = post.pubDate.formatRssDateToKorean())
                        }
                }
                newPostList.sortBy { it.pubDate }

                _state.value = FollowingFeedState.Feed(newPostList)
            }.catch {
                _state.value = FollowingFeedState.Empty
                // TODO 에러
            }.launchIn(viewModelScope)
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

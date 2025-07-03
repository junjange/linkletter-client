package linkletter.client.feature.blogfollow

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
import linkletter.client.core.domain.usecase.GetBlogUseCase
import linkletter.client.feature.blogfollow.model.BlogFollowEffect
import linkletter.client.feature.blogfollow.model.BlogFollowEvent
import linkletter.client.feature.blogfollow.model.BlogFollowMessage
import linkletter.client.feature.blogfollow.model.BlogFollowState

class BlogFollowViewModel(
    private val getBlogUseCase: GetBlogUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<BlogFollowState>(BlogFollowState.Empty)
    val state = _state.asStateFlow()

    private val _effect = Channel<BlogFollowEffect>(Channel.BUFFERED)
    val effect get() = _effect.receiveAsFlow()

    fun onEvent(event: BlogFollowEvent) {
        when (event) {
            is BlogFollowEvent.Search -> fetchBlogFollow(url = event.query)
            is BlogFollowEvent.BackClicked -> back()
            is BlogFollowEvent.BlogClicked -> openUri(link = event.link)
            is BlogFollowEvent.BlogFollowToggleClicked -> toggleBlogFollow()
        }
    }

    private fun fetchBlogFollow(url: String) {
        getBlogUseCase(url = url)
            .onStart {
                _state.value = BlogFollowState.Loading
                delay(1000)
            }.onEach { blog ->
                val postList =
                    blog.postList.map { post ->
                        post.copy(pubDate = post.pubDate.formatRssDateToKorean())
                    }
                val newState =
                    BlogFollowState.BlogFollow(
                        blog = blog.copy(postList = postList),
                        isFollowed = false,
                    )
                _state.value = newState
            }.catch { error ->
                _state.value = BlogFollowState.Empty
                viewModelScope.launch {
                    _effect.send(
                        BlogFollowEffect.ShowMessage(
                            BlogFollowMessage.BlogNotFound,
                        ),
                    )
                }
            }.launchIn(viewModelScope)
    }

    private fun back() {
        viewModelScope.launch {
            _effect.send(BlogFollowEffect.NavigateBack)
        }
    }

    private fun openUri(link: String) {
        viewModelScope.launch {
            _effect.send(BlogFollowEffect.OpenUri(link))
        }
    }

    private fun toggleBlogFollow() {
        viewModelScope.launch {
            if (_state.value is BlogFollowState.BlogFollow) {
                val currentState = _state.value as BlogFollowState.BlogFollow
                val newIsFollowed = !currentState.isFollowed

                if (newIsFollowed) {
                    _effect.send(
                        BlogFollowEffect.ShowMessage(
                            BlogFollowMessage.BlogFollowed(
                                blogName = currentState.blog.name,
                            ),
                        ),
                    )
                } else {
                    _effect.send(
                        BlogFollowEffect.ShowMessage(
                            BlogFollowMessage.BlogUnfollowed(
                                blogName = currentState.blog.name,
                            ),
                        ),
                    )
                }

                _state.value = currentState.copy(isFollowed = newIsFollowed)
            }
        }
    }
}

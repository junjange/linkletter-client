package linkletter.client.feature.blogfollow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import linkletter.client.core.common.formatRssDateToKorean
import linkletter.client.core.domain.usecase.DeleteBlogInfoUseCase
import linkletter.client.core.domain.usecase.GetAllBlogInfosUseCase
import linkletter.client.core.domain.usecase.GetBlogUseCase
import linkletter.client.core.domain.usecase.InsertBlogInfoUseCase
import linkletter.client.core.model.BlogInfo
import linkletter.client.feature.blogfollow.model.BlogFollowEffect
import linkletter.client.feature.blogfollow.model.BlogFollowEvent
import linkletter.client.feature.blogfollow.model.BlogFollowMessage
import linkletter.client.feature.blogfollow.model.BlogFollowState
import kotlin.collections.map

class BlogFollowViewModel(
    private val getBlogUseCase: GetBlogUseCase,
    private val getAllBlogInfosUseCase: GetAllBlogInfosUseCase,
    private val insertBlogInfoUseCase: InsertBlogInfoUseCase,
    private val deleteBlogInfoUseCase: DeleteBlogInfoUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<BlogFollowState>(BlogFollowState.Empty)
    val state = _state.asStateFlow()

    private val _effect = Channel<BlogFollowEffect>(Channel.BUFFERED)
    val effect get() = _effect.receiveAsFlow()

    val blogUrlList: StateFlow<List<String>> =
        getAllBlogInfosUseCase()
            .map { infos ->
                infos.map { it.url }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = emptyList(),
            )

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
            }.onEach { blog ->
                val isFollowed = blog.url in blogUrlList.value
                val postList =
                    blog.postList.map { post ->
                        post.copy(pubDate = post.pubDate.formatRssDateToKorean())
                    }

                val newState =
                    BlogFollowState.BlogFollow(
                        blog = blog.copy(postList = postList),
                        isFollowed = isFollowed,
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
            if (state.value is BlogFollowState.BlogFollow) {
                val currentState = state.value as BlogFollowState.BlogFollow
                val newIsFollowed = !currentState.isFollowed
                val blogInfo =
                    BlogInfo(
                        name = currentState.blog.name,
                        author = currentState.blog.author,
                        url = currentState.blog.url,
                    )
                val blogUrl = currentState.blog.url

                if (newIsFollowed) {
                    insertBlogInfoUseCase(blogInfo = blogInfo)

                    _effect.send(
                        BlogFollowEffect.ShowMessage(
                            BlogFollowMessage.BlogFollowed(
                                blogName = currentState.blog.name,
                            ),
                        ),
                    )
                } else {
                    deleteBlogInfoUseCase(blogUrl = blogUrl)

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

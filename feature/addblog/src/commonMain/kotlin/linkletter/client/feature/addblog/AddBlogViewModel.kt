package linkletter.client.feature.addblog

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
import linkletter.client.feature.addblog.model.AddBlogEffect
import linkletter.client.feature.addblog.model.AddBlogEvent
import linkletter.client.feature.addblog.model.AddBlogMessage
import linkletter.client.feature.addblog.model.AddBlogState
import kotlin.collections.map

class AddBlogViewModel(
    getAllBlogInfosUseCase: GetAllBlogInfosUseCase,
    private val getBlogUseCase: GetBlogUseCase,
    private val insertBlogInfoUseCase: InsertBlogInfoUseCase,
    private val deleteBlogInfoUseCase: DeleteBlogInfoUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<AddBlogState>(AddBlogState.Empty)
    val state = _state.asStateFlow()

    private val _effect = Channel<AddBlogEffect>(Channel.BUFFERED)
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

    fun onEvent(event: AddBlogEvent) {
        when (event) {
            is AddBlogEvent.Search -> fetchBlog(url = event.query)
            is AddBlogEvent.BackClicked -> back()
            is AddBlogEvent.AddBlogClicked -> openUri(link = event.link)
            is AddBlogEvent.AddBlogToggleClicked -> toggleBlogAdd()
        }
    }

    private fun fetchBlog(url: String) {
        getBlogUseCase(url = url)
            .onStart {
                _state.value = AddBlogState.Loading
            }.onEach { blog ->
                val isFollowed = blog.url in blogUrlList.value
                val postList =
                    blog.postList.map { post ->
                        post.copy(pubDate = post.pubDate.formatRssDateToKorean())
                    }

                val newState =
                    AddBlogState.AddBlog(
                        blog = blog.copy(postList = postList),
                        isFollowed = isFollowed,
                    )
                _state.value = newState
            }.catch { error ->
                _state.value = AddBlogState.Empty
                viewModelScope.launch {
                    _effect.send(
                        AddBlogEffect.ShowMessage(
                            AddBlogMessage.NotFound,
                        ),
                    )
                }
            }.launchIn(viewModelScope)
    }

    private fun back() {
        viewModelScope.launch {
            _effect.send(AddBlogEffect.NavigateBack)
        }
    }

    private fun openUri(link: String) {
        viewModelScope.launch {
            _effect.send(AddBlogEffect.OpenUri(link))
        }
    }

    private fun toggleBlogAdd() {
        viewModelScope.launch {
            if (state.value is AddBlogState.AddBlog) {
                val currentState = state.value as AddBlogState.AddBlog
                val newIsFollowed = !currentState.isFollowed

                if (newIsFollowed) {
                    insertBlogInfoUseCase(blog = currentState.blog)

                    _effect.send(
                        AddBlogEffect.ShowMessage(
                            AddBlogMessage.Followed(
                                blogName = currentState.blog.name,
                            ),
                        ),
                    )
                } else {
                    val blogUrl = currentState.blog.url

                    deleteBlogInfoUseCase(blogUrl = blogUrl)

                    _effect.send(
                        AddBlogEffect.ShowMessage(
                            AddBlogMessage.Unfollowed(
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

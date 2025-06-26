package linkletter.client.feature.blogfollow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import linkletter.client.feature.blogfollow.model.BlogFollowEffect
import linkletter.client.feature.blogfollow.model.BlogFollowEvent
import linkletter.client.feature.blogfollow.model.BlogFollowMessage
import linkletter.client.feature.blogfollow.model.BlogFollowState
import linkletter.client.feature.blogfollow.model.BlogFollowUiModel

class BlogFollowViewModel : ViewModel() {
    private val _state = MutableStateFlow<BlogFollowState>(BlogFollowState())
    val state = _state.asStateFlow()

    private val _effect = Channel<BlogFollowEffect>(Channel.BUFFERED)
    val effect get() = _effect.receiveAsFlow()

    init {
        fetchBlogList()
    }

    fun onEvent(event: BlogFollowEvent) {
        when (event) {
            is BlogFollowEvent.BackClicked -> back()
            is BlogFollowEvent.BlogClicked -> openUri(link = event.link)
            is BlogFollowEvent.BlogFollowToggleClicked -> toggleBlogFollow(blogFollowUiModel = event.blogFollowUiModel)
        }
    }

    private fun fetchBlogList() {
        _state.value = BlogFollowState.Dummy
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

    private fun toggleBlogFollow(blogFollowUiModel: BlogFollowUiModel) {
        viewModelScope.launch {
            val blogs =
                state.value.blogFollowUiModelList.map {
                    if (it.id == blogFollowUiModel.id) {
                        it.copy(isFollowed = !it.isFollowed)
                    } else {
                        it
                    }
                }

            if (!blogFollowUiModel.isFollowed) {
                _effect.send(BlogFollowEffect.ShowMessage(BlogFollowMessage.BlogFollowed(blogName = blogFollowUiModel.name)))
            }
            _state.value = state.value.copy(blogFollowUiModelList = blogs)
        }
    }
}

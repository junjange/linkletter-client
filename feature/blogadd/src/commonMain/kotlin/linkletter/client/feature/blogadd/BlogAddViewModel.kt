package linkletter.client.feature.blogadd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import linkletter.client.feature.blogadd.model.Blog
import linkletter.client.feature.blogadd.model.BlogAddEffect
import linkletter.client.feature.blogadd.model.BlogAddEvent
import linkletter.client.feature.blogadd.model.BlogAddState

class BlogAddViewModel : ViewModel() {
    private val _state = MutableStateFlow<BlogAddState>(BlogAddState())
    val state = _state.asStateFlow()

    private val _effect = Channel<BlogAddEffect>(Channel.BUFFERED)
    val effect get() = _effect.receiveAsFlow()

    init {
        fetchBlogList()
    }

    fun onEvent(event: BlogAddEvent) {
        when (event) {
            is BlogAddEvent.BackClicked -> back()
            is BlogAddEvent.BlogClicked -> openUri(link = event.link)
            is BlogAddEvent.BlogFollowToggleClicked -> toggleBlogFollow(blog = event.blog)
        }
    }

    private fun fetchBlogList() {
        _state.value = BlogAddState.Dummy
    }

    private fun back() {
        viewModelScope.launch {
            _effect.send(BlogAddEffect.NavigateBack)
        }
    }

    private fun openUri(link: String) {
        viewModelScope.launch {
            _effect.send(BlogAddEffect.OpenUri(link))
        }
    }

    private fun toggleBlogFollow(blog: Blog) {
        viewModelScope.launch {
            val blogs =
                state.value.blogList.map {
                    if (it == blog) {
                        it.copy(isFollowed = !it.isFollowed)
                    } else {
                        it
                    }
                }

            _state.value = state.value.copy(blogList = blogs)
        }
    }
}

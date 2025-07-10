package linkletter.client.feature.postarchive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import linkletter.client.feature.postarchive.model.PostArchiveEffect
import linkletter.client.feature.postarchive.model.PostArchiveEvent
import linkletter.client.feature.postarchive.model.PostArchiveState

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class PostArchiveViewModel : ViewModel() {
    private val _state = MutableStateFlow<PostArchiveState>(PostArchiveState.Loading)
    val state = _state.asStateFlow()

    private val _effect = Channel<PostArchiveEffect>(Channel.BUFFERED)
    val effect get() = _effect.receiveAsFlow()

    fun onEvent(event: PostArchiveEvent) {
        when (event) {
            is PostArchiveEvent.Refresh -> {}
            is PostArchiveEvent.PostClicked -> openUri(link = event.link)
            is PostArchiveEvent.AddBlogClicked -> navigateToAddBlog()
        }
    }

    private fun navigateToAddBlog() {
        viewModelScope.launch {
            _effect.send(PostArchiveEffect.NavigateToAddBlog)
        }
    }

    private fun openUri(link: String) {
        viewModelScope.launch {
            _effect.send(PostArchiveEffect.OpenUri(link))
        }
    }
}

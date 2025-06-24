package linkletter.client.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import linkletter.client.core.common.formatRssDateToKorean
import linkletter.client.core.domain.usecase.GetPostsUseCase
import linkletter.client.feature.home.model.HomeEffect
import linkletter.client.feature.home.model.HomeEvent
import linkletter.client.feature.home.model.HomeState

class HomeViewModel(
    private val getPostsUseCase: GetPostsUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    val state = _state.asStateFlow()

    private val _effect = Channel<HomeEffect>(Channel.BUFFERED)
    val effect get() = _effect.receiveAsFlow()

    init {
        fetchFeed()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.PostClicked -> openUri(event.link)
        }
    }

    private fun fetchFeed() {
        getPostsUseCase(url = "https://fre2-dom.tistory.com/")
            .onEach { posts ->
                val newPosts =
                    posts.map { post ->
                        post.copy(pubDate = post.pubDate.formatRssDateToKorean())
                    }
                _state.value = HomeState.Feed(newPosts)
            }.catch {
                // TODO 에러
            }.launchIn(viewModelScope)
    }

    private fun openUri(link: String) {
        viewModelScope.launch {
            _effect.send(HomeEffect.OpenUri(link))
        }
    }
}

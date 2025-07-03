package linkletter.client.feature.home

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
import linkletter.client.feature.home.model.HomeEffect
import linkletter.client.feature.home.model.HomeEvent
import linkletter.client.feature.home.model.HomeState

class HomeViewModel(
    private val getBlogUseCase: GetBlogUseCase,
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
            is HomeEvent.FeedRefresh -> fetchFeed()
            is HomeEvent.PostClicked -> openUri(event.link)
            is HomeEvent.AddBlogClicked -> navigateToAddBlog()
        }
    }

    private fun fetchFeed() {
        getBlogUseCase(url = "https://fre2-dom.tistory.com/")
            .onStart {
                _state.value = HomeState.Loading
                delay(1000)
            }.onEach { blog ->
                val postList =
                    blog.postList.map { post ->
                        post.copy(pubDate = post.pubDate.formatRssDateToKorean())
                    }
                _state.value = HomeState.Feed(blog.copy(postList = postList))
            }.catch {
                // TODO 에러
            }.launchIn(viewModelScope)
    }

    private fun navigateToAddBlog() {
        viewModelScope.launch {
            _effect.send(HomeEffect.NavigateToAddBlog)
        }
    }

    private fun openUri(link: String) {
        viewModelScope.launch {
            _effect.send(HomeEffect.OpenUri(link))
        }
    }
}

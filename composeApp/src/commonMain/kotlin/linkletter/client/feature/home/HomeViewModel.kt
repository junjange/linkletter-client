package linkletter.client.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import linkletter.client.feature.home.model.HomeEffect
import linkletter.client.feature.home.model.HomeEvent
import linkletter.client.feature.home.model.HomeState

class HomeViewModel : ViewModel() {
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
        _state.value = HomeState.Feed.Dummy
    }

    private fun openUri(link: String) {
        viewModelScope.launch {
            _effect.send(HomeEffect.OpenUri(link))
        }
    }
}

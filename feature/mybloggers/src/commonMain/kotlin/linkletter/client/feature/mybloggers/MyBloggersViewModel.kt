package linkletter.client.feature.mybloggers

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
import linkletter.client.core.domain.usecase.DeleteBlogInfoUseCase
import linkletter.client.core.domain.usecase.GetAllBlogInfosUseCase
import linkletter.client.core.domain.usecase.InsertBlogInfoUseCase
import linkletter.client.core.model.BlogInfo
import linkletter.client.feature.mybloggers.model.Blogger
import linkletter.client.feature.mybloggers.model.MyBloggersEffect
import linkletter.client.feature.mybloggers.model.MyBloggersEvent
import linkletter.client.feature.mybloggers.model.MyBloggersState
import linkletter.client.feature.mybloggers.model.toBlogger

class MyBloggersViewModel(
    private val getAllBlogInfosUseCase: GetAllBlogInfosUseCase,
    private val insertBlogInfoUseCase: InsertBlogInfoUseCase,
    private val deleteBlogInfoUseCase: DeleteBlogInfoUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<MyBloggersState>(MyBloggersState.Loading)
    val state = _state.asStateFlow()

    private val _effect = Channel<MyBloggersEffect>(Channel.BUFFERED)
    val effect get() = _effect.receiveAsFlow()

    init {
        fetchMyBloggers()
    }

    fun onEvent(event: MyBloggersEvent) {
        when (event) {
            is MyBloggersEvent.BloggerClicked -> openUri(link = event.link)
            is MyBloggersEvent.BloggerToggleClicked -> toggleMyBlogger(blogger = event.blogger)
            is MyBloggersEvent.Refresh -> fetchMyBloggers()
        }
    }

    private fun fetchMyBloggers() {
        getAllBlogInfosUseCase()
            .onStart {
                _state.value = MyBloggersState.Loading
                delay(300)
            }.onEach { blogInfos: List<BlogInfo> ->
                _state.value =
                    if (blogInfos.isEmpty()) {
                        MyBloggersState.Empty
                    } else {
                        MyBloggersState.Loaded(blogInfos.map { it.toBlogger(isFollowed = true) })
                    }
            }.catch { error ->
                _state.value = MyBloggersState.Empty
            }.launchIn(viewModelScope)
    }

    private fun openUri(link: String) {
        viewModelScope.launch {
            _effect.send(MyBloggersEffect.OpenUri(link))
        }
    }

    private fun toggleMyBlogger(blogger: Blogger) {
        viewModelScope.launch {
            deleteBlogInfoUseCase(blogUrl = blogger.url)
        }
    }
}

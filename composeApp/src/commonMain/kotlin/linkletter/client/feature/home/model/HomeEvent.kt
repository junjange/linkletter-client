package linkletter.client.feature.home.model

sealed interface HomeEvent {
    data class PostClicked(
        val link: String,
    ) : HomeEvent
}

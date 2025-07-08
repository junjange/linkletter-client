package linkletter.client.feature.mybloggers.model

sealed interface MyBloggersEvent {
    data object Refresh : MyBloggersEvent

    data class BloggerClicked(
        val link: String,
    ) : MyBloggersEvent

    data class BloggerToggleClicked(
        val blogger: Blogger,
    ) : MyBloggersEvent
}

package linkletter.client.feature.postarchive.model

sealed interface PostArchiveEvent {
    data object Refresh : PostArchiveEvent

    data class PostClicked(
        val link: String,
    ) : PostArchiveEvent

    data object AddBlogClicked : PostArchiveEvent
}

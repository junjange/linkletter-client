package linkletter.client.feature.addblog.model

sealed interface AddBlogEvent {
    data class Search(
        val query: String,
    ) : AddBlogEvent

    data object BackClicked : AddBlogEvent

    data class AddBlogClicked(
        val link: String,
    ) : AddBlogEvent

    data object AddBlogToggleClicked : AddBlogEvent
}

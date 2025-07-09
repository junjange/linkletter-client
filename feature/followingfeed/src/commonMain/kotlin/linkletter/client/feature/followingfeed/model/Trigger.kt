package linkletter.client.feature.followingfeed.model

sealed class Trigger {
    data class Search(
        val query: String,
    ) : Trigger()

    data object Refresh : Trigger()
}

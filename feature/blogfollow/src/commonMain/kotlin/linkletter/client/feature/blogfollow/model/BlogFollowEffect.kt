package linkletter.client.feature.blogfollow.model

sealed interface BlogFollowEffect {
    data object NavigateBack : BlogFollowEffect

    data class OpenUri(
        val link: String,
    ) : BlogFollowEffect

    data class ShowMessage(
        val message: BlogFollowMessage,
    ) : BlogFollowEffect
}

package linkletter.client.feature.postarchive.model

sealed interface PostArchiveEffect {
    data class OpenUri(
        val link: String,
    ) : PostArchiveEffect

    data object NavigateToAddBlog : PostArchiveEffect
}

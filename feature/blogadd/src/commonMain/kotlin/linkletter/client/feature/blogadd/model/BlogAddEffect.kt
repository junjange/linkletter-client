package linkletter.client.feature.blogadd.model

sealed interface BlogAddEffect {
    data object NavigateBack : BlogAddEffect

    data class OpenUri(
        val link: String,
    ) : BlogAddEffect

    data class ShowMessage(
        val message: BlogAddMessage,
    ) : BlogAddEffect
}

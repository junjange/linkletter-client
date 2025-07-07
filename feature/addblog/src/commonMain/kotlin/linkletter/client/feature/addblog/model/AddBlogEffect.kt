package linkletter.client.feature.addblog.model

sealed interface AddBlogEffect {
    data object NavigateBack : AddBlogEffect

    data class OpenUri(
        val link: String,
    ) : AddBlogEffect

    data class ShowMessage(
        val message: AddBlogMessage,
    ) : AddBlogEffect
}

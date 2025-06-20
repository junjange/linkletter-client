package linkletter.client.feature.home.model

sealed interface HomeEffect {
    data class OpenUri(
        val link: String,
    ) : HomeEffect
}

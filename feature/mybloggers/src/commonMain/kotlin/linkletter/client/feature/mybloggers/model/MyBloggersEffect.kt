package linkletter.client.feature.mybloggers.model

sealed interface MyBloggersEffect {
    data class OpenUri(
        val link: String,
    ) : MyBloggersEffect
}

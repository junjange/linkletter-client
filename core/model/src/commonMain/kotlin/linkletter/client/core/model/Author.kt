package linkletter.client.core.model

data class Author(
    val name: String,
    val imageUrl: String?,
) {
    companion object {
        val Default =
            Author(
                name = "",
                imageUrl = "",
            )
    }
}

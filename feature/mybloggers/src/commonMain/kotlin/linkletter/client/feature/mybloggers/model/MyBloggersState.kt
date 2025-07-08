package linkletter.client.feature.mybloggers.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import linkletter.client.core.model.Author
import linkletter.client.core.model.BlogInfo

@Stable
sealed interface MyBloggersState {
    @Immutable
    data object Loading : MyBloggersState

    @Immutable
    data object Empty : MyBloggersState

    @Immutable
    data class Loaded(
        val bloggerList: List<Blogger>,
    ) : MyBloggersState
}

data class Blogger(
    val name: String,
    val author: Author,
    val url: String,
    val isFollowed: Boolean,
) {
    companion object {
        val Default =
            List(10) {
                Blogger(
                    name = "",
                    author = Author.Default,
                    url = "",
                    isFollowed = false,
                )
            }
    }
}

fun BlogInfo.toBlogger(isFollowed: Boolean) =
    Blogger(
        name = this.name,
        author = this.author,
        url = this.url,
        isFollowed = isFollowed,
    )

fun Blogger.toBlogInfo() =
    BlogInfo(
        name = this.name,
        author = this.author,
        url = this.url,
    )

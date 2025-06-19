package linkletter.client.feature.home.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface HomeUiState {
    @Immutable
    data object Loading : HomeUiState

    @Immutable
    data class Feed(
        val posts: List<Post>,
    ) : HomeUiState {
        @Immutable
        data class Post(
            val author: Author,
            val title: String,
            val description: String,
            val link: String,
            val thumbnailUrl: String?,
            val pubDate: String,
        ) {
            companion object {
                val Default =
                    Post(
                        author = Author.Default,
                        title = "",
                        description = "",
                        link = "",
                        thumbnailUrl = "",
                        pubDate = "",
                    )
                val Dummy =
                    Post(
                        author = Author.Dummy,
                        title = "Compose Multiplatform 도입기",
                        description = "이 경험은 예전에 내가 GDG on Campus에서 해커톤을 기획하며 심사위원을 섭외했던 때와 비교되는 큰 변화였다. 당시에는 심사위원을 구하는 것이 쉽지 않았고, 많은 노력이 필요했지만, 올해는 내가 직접 심사위원 역할을 맡게 되어 조금은 벅차기도 했다. 큐시즘은 기획자, 디자이너, 개발자가 한 팀을 이루는 방식으로 진행되었고, 학생들의 실력이 매우 뛰어나 직장인들이 포함된 동아리와 비교해도 손색이 없을 정도였다. 심사위원으로 처음 맡게 되었을 때는 내가 이 역할을 잘할 수 있을지 의문이 들었지만, 그동안 쌓아온 개발 경험을 바탕으로 최선을 다해 평가하고 피드백을 제공했다. 이 경험을 통해, 기술과 협업 능력을 갖춘 참가자들의 실력을 직접 느낄 수 있었고, 심사 과정에서 내가 배운 점도 많았다. 결국, 이번 경험은 내 자신에게 큰 도전이었고, 더 나아가 내가 가진 지식과 경험을 공유하는 기회로서 값진 시간이 되었다.",
                        link = "https://fre2-dom.tistory.com/579",
                        thumbnailUrl = "https://blog.kakaocdn.net/dn/kZIrl/btsL4Jla8ns/glTiQnUFfC3VXDKHLPHyiK/img.jpg",
                        pubDate = "2025년 6월 18일",
                    )
            }
        }

        @Immutable
        data class Author(
            val name: String,
            val imageUrl: String,
        ) {
            companion object {
                val Default =
                    Author(
                        name = "",
                        imageUrl = "",
                    )
                val Dummy =
                    Author(
                        name = "JunJangE",
                        imageUrl = "https://tistory1.daumcdn.net/tistory/3096830/attach/d217dd7eba234cc9b332553d14ef1de6",
                    )
            }
        }

        companion object {
            val Dummy =
                Feed(
                    posts =
                        List(10) {
                            if (it % 2 == 0) {
                                Post.Dummy.copy(thumbnailUrl = null)
                            } else {
                                Post.Dummy
                            }
                        },
                )
        }
    }
}

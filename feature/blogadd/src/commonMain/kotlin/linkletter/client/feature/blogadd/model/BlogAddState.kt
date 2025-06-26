package linkletter.client.feature.blogadd.model

data class BlogAddState(
    val blogList: List<Blog> = emptyList(),
) {
    companion object {
        val Dummy =
            BlogAddState(
                listOf(
                    Blog(
                        0L,
                        "조준장 개발자 생존기",
                        "https://fre2-dom.tistory.com",
                        "https://tistory1.daumcdn.net/tistory/3096830/attach/d217dd7eba234cc9b332553d14ef1de6",
                        false,
                    ),
                    Blog(
                        1L,
                        "junjange.log",
                        "https://velog.io/@wnswkd486",
                        "https://velog.velcdn.com/images/wnswkd486/profile/d5beb5d3-03a1-49c1-9d91-71ec5aff3052/social_profile.jpeg",
                        false,
                    ),
                    Blog(
                        3L,
                        "wnswkd486",
                        "https://blog.naver.com/wnswkd486",
                        null,
                        false,
                    ),
                ) +
                    List(10) {
                        Blog(
                            4L + it,
                            "조준장 개발자 생존기",
                            "https://fre2-dom.tistory.com",
                            "https://tistory1.daumcdn.net/tistory/3096830/attach/d217dd7eba234cc9b332553d14ef1de6",
                            false,
                        )
                    },
            )
    }
}

data class Blog(
    val id: Long,
    val name: String,
    val link: String,
    val imageUrl: String?,
    val isFollowed: Boolean,
)

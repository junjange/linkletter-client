package linkletter.client.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object AddBlog : Route
}

sealed interface MainTabRoute : Route {
    @Serializable
    data object FollowingFeed : MainTabRoute

    @Serializable
    data object MyBloggers : MainTabRoute

    @Serializable
    data object Setting : MainTabRoute
}

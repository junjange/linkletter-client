package linkletter.client.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object BlogAdd : Route
}

sealed interface MainTabRoute : Route {
    @Serializable
    data object Home : MainTabRoute

    @Serializable
    data object Bookmark : MainTabRoute
}

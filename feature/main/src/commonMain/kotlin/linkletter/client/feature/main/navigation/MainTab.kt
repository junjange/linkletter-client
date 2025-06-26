package linkletter.client.feature.main.navigation

import androidx.compose.runtime.Composable
import linkletter.client.core.navigation.MainTabRoute
import linkletter.client.core.navigation.Route
import linkletter_client.feature.main.generated.resources.Res
import linkletter_client.feature.main.generated.resources.ic_bookmark
import linkletter_client.feature.main.generated.resources.ic_home
import org.jetbrains.compose.resources.DrawableResource

enum class MainTab(
    val iconResId: DrawableResource,
    val contentDescription: String,
    val route: MainTabRoute,
) {
    HOME(
        iconResId = Res.drawable.ic_home,
        contentDescription = "홈",
        route = MainTabRoute.Home,
    ),
    BOOKMARK(
        iconResId = Res.drawable.ic_bookmark,
        contentDescription = "북마크",
        route = MainTabRoute.Bookmark,
    ),
    ;

    companion object {
        @Composable
        fun find(predicate: @Composable (MainTabRoute) -> Boolean): MainTab? = entries.find { predicate(it.route) }

        @Composable
        fun contains(predicate: @Composable (Route) -> Boolean): Boolean = entries.map { it.route }.any { predicate(it) }
    }
}

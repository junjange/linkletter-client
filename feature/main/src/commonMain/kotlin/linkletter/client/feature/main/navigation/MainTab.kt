package linkletter.client.feature.main.navigation

import androidx.compose.runtime.Composable
import linkletter.client.core.navigation.MainTabRoute
import linkletter.client.core.navigation.Route
import linkletter_client.feature.main.generated.resources.Res
import linkletter_client.feature.main.generated.resources.ic_following_feed
import linkletter_client.feature.main.generated.resources.ic_groups
import linkletter_client.feature.main.generated.resources.ic_settings
import org.jetbrains.compose.resources.DrawableResource

enum class MainTab(
    val iconResId: DrawableResource,
    val contentDescription: String,
    val route: MainTabRoute,
) {
    FOLLOWING_FEED(
        iconResId = Res.drawable.ic_following_feed,
        contentDescription = "팔로잉 피드",
        route = MainTabRoute.FollowingFeed,
    ),
    MY_BLOGGERS(
        iconResId = Res.drawable.ic_groups,
        contentDescription = "내 블로거",
        route = MainTabRoute.MyBloggers,
    ),

    SETTING(
        iconResId = Res.drawable.ic_settings,
        contentDescription = "설정",
        route = MainTabRoute.Setting,
    ),
    ;

    companion object {
        @Composable
        fun find(predicate: @Composable (MainTabRoute) -> Boolean): MainTab? = entries.find { predicate(it.route) }

        @Composable
        fun contains(predicate: @Composable (Route) -> Boolean): Boolean = entries.map { it.route }.any { predicate(it) }
    }
}

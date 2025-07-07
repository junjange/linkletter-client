package linkletter.client.feature.followingfeed.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import linkletter.client.core.navigation.MainTabRoute
import linkletter.client.feature.followingfeed.FollowingFeedScreen

fun NavController.navigateFollowingFeed(navOptions: NavOptions) {
    navigate(MainTabRoute.FollowingFeed, navOptions)
}

fun NavGraphBuilder.followingFeedNavGraph(onBlogFollowClick: () -> Unit) {
    composable<MainTabRoute.FollowingFeed> {
        FollowingFeedScreen(onBlogFollowClick = onBlogFollowClick)
    }
}

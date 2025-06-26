package linkletter.client.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import linkletter.client.core.navigation.MainTabRoute
import linkletter.client.feature.home.HomeScreen

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(MainTabRoute.Home, navOptions)
}

fun NavGraphBuilder.homeNavGraph(onBlogFollowClick: () -> Unit) {
    composable<MainTabRoute.Home> {
        HomeScreen(onBlogFollowClick = onBlogFollowClick)
    }
}

package linkletter.client.feature.mybloggers.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import linkletter.client.core.navigation.MainTabRoute
import linkletter.client.feature.mybloggers.MyBloggersScreen

fun NavController.navigateMyBloggers(navOptions: NavOptions) {
    navigate(MainTabRoute.MyBloggers, navOptions)
}

fun NavGraphBuilder.myBloggersScreenNavGraph() {
    composable<MainTabRoute.MyBloggers> {
        MyBloggersScreen()
    }
}

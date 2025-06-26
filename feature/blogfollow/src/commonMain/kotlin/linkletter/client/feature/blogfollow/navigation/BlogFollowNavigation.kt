package linkletter.client.feature.blogfollow.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import linkletter.client.core.navigation.Route
import linkletter.client.feature.blogfollow.BlogFollowScreen

fun NavController.navigateBlogFollow() {
    this.navigate(Route.BlogFollow)
}

fun NavGraphBuilder.blogFollowNavGraph(onBackClick: () -> Unit) {
    composable<Route.BlogFollow> {
        BlogFollowScreen(onBackClick = onBackClick)
    }
}

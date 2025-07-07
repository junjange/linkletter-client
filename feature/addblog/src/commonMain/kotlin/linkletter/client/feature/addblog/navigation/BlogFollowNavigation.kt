package linkletter.client.feature.addblog.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import linkletter.client.core.navigation.Route
import linkletter.client.feature.addblog.AddBlogScreen

fun NavController.navigateAddBlog() {
    this.navigate(Route.AddBlog)
}

fun NavGraphBuilder.addBlogNavGraph(onBackClick: () -> Unit) {
    composable<Route.AddBlog> {
        AddBlogScreen(onBackClick = onBackClick)
    }
}

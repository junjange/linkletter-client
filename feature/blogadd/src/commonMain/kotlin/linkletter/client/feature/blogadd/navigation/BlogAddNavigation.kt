package linkletter.client.feature.blogadd.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import linkletter.client.core.navigation.Route
import linkletter.client.feature.blogadd.BlogAddScreen

fun NavController.navigateBlogAdd() {
    this.navigate(Route.BlogAdd)
}

fun NavGraphBuilder.blogAddNavGraph(onBackClick: () -> Unit) {
    composable<Route.BlogAdd> {
        BlogAddScreen(onBackClick = onBackClick)
    }
}

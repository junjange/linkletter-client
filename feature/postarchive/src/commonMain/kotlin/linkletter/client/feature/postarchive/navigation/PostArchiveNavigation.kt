package linkletter.client.feature.postarchive.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import linkletter.client.core.navigation.MainTabRoute
import linkletter.client.feature.postarchive.PostArchiveScreen

fun NavController.navigatePostArchive(navOptions: NavOptions) {
    navigate(MainTabRoute.PostArchive, navOptions)
}

fun NavGraphBuilder.postArchiveNavGraph(navigateToAddBlog: () -> Unit) {
    composable<MainTabRoute.PostArchive> {
        PostArchiveScreen(navigateToAddBlog = navigateToAddBlog)
    }
}

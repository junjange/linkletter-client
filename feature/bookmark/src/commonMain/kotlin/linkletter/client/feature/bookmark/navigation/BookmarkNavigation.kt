package linkletter.client.feature.bookmark.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import linkletter.client.core.navigation.MainTabRoute
import linkletter.client.feature.bookmark.BookmarkScreen

fun NavController.navigateBookmark(navOptions: NavOptions) {
    navigate(MainTabRoute.Bookmark, navOptions)
}

fun NavGraphBuilder.bookmarkNavGraph() {
    composable<MainTabRoute.Bookmark> {
        BookmarkScreen()
    }
}

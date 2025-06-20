package linkletter.client.feature.main.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import linkletter.client.feature.bookmark.navigation.bookmarkNavGraph
import linkletter.client.feature.home.navigation.homeNavGraph
import linkletter.client.feature.main.navigation.MainNavigator

@Composable
fun MainNavHost(
    navigator: MainNavigator,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navigator.navController,
        startDestination = navigator.startDestination,
    ) {
        homeNavGraph()
        bookmarkNavGraph()
    }
}

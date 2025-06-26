package linkletter.client.feature.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import linkletter.client.core.navigation.MainTabRoute
import linkletter.client.core.navigation.Route
import linkletter.client.feature.blogadd.navigation.navigateBlogAdd
import linkletter.client.feature.bookmark.navigation.navigateBookmark
import linkletter.client.feature.home.navigation.navigateHome

class MainNavigator(
    val navController: NavHostController,
) {
    private val currentDestination: NavDestination?
        @Composable get() =
            navController
                .currentBackStackEntryAsState()
                .value
                ?.destination

    val startDestination = MainTab.HOME.route

    val currentTab: MainTab?
        @Composable get() =
            MainTab.find { tab ->
                currentDestination?.hasRoute(tab::class) == true
            }

    fun navigate(tab: MainTab) {
        val navOptions =
            navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }

        when (tab) {
            MainTab.HOME -> navController.navigateHome(navOptions)
            MainTab.BOOKMARK -> navController.navigateBookmark(navOptions)
        }
    }

    private fun popBackStack() {
        navController.popBackStack()
    }

    fun navigateBlogAdd() {
        navController.navigateBlogAdd()
    }

    fun popBackStackIfNotHome() {
        if (!isSameCurrentDestination<MainTabRoute.Home>()) {
            popBackStack()
        }
    }

    private inline fun <reified T : Route> isSameCurrentDestination(): Boolean = navController.currentDestination?.hasRoute<T>() == true

    @Composable
    fun shouldShowBottomBar() =
        MainTab.contains {
            currentDestination?.hasRoute(it::class) == true
        }
}

@Composable
fun rememberMainNavigator(navController: NavHostController = rememberNavController()): MainNavigator =
    remember(navController) {
        MainNavigator(navController)
    }

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
import linkletter.client.feature.addblog.navigation.navigateAddBlog
import linkletter.client.feature.followingfeed.navigation.navigateFollowingFeed
import linkletter.client.feature.mybloggers.navigation.navigateMyBloggers
import linkletter.client.feature.setting.navigation.navigateSetting

class MainNavigator(
    val navController: NavHostController,
) {
    private val currentDestination: NavDestination?
        @Composable get() =
            navController
                .currentBackStackEntryAsState()
                .value
                ?.destination

    val startDestination = MainTab.FOLLOWING_FEED.route

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
            MainTab.FOLLOWING_FEED -> navController.navigateFollowingFeed(navOptions)
            MainTab.MY_BLOGGERS -> navController.navigateMyBloggers(navOptions)
            MainTab.SETTING -> navController.navigateSetting(navOptions)
        }
    }

    private fun popBackStack() {
        navController.popBackStack()
    }

    fun navigateAddBlog() {
        navController.navigateAddBlog()
    }

    fun popBackStackIfNotFollowingFeed() {
        if (!isSameCurrentDestination<MainTabRoute.FollowingFeed>()) {
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

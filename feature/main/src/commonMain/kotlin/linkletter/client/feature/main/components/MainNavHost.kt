package linkletter.client.feature.main.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import linkletter.client.feature.addblog.navigation.addBlogNavGraph
import linkletter.client.feature.followingfeed.navigation.followingFeedNavGraph
import linkletter.client.feature.main.navigation.MainNavigator
import linkletter.client.feature.mybloggers.navigation.myBloggersScreenNavGraph
import linkletter.client.feature.setting.navigation.settingNavGraph

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
        followingFeedNavGraph(
            navigateToAddBlog = navigator::navigateAddBlog,
        )
        myBloggersScreenNavGraph()
        settingNavGraph()
        addBlogNavGraph(
            onBackClick = navigator::popBackStackIfNotFollowingFeed,
        )
    }
}

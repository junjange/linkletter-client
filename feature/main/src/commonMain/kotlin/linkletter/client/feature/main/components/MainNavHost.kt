package linkletter.client.feature.main.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import linkletter.client.feature.blogfollow.navigation.blogFollowNavGraph
import linkletter.client.feature.followingfeed.navigation.followingFeedNavGraph
import linkletter.client.feature.main.navigation.MainNavigator
import linkletter.client.feature.mybloggers.navigation.myBloggersScreenNavGraph

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
            onBlogFollowClick = navigator::navigateBlogFollow,
        )
        myBloggersScreenNavGraph()
        blogFollowNavGraph(
            onBackClick = navigator::popBackStackIfNotFollowingFeed,
        )
    }
}

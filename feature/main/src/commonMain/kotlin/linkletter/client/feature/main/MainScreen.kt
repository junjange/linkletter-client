package linkletter.client.feature.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import linkletter.client.core.designsystem.theme.LinkletterTheme
import linkletter.client.feature.main.components.MainBottomBar
import linkletter.client.feature.main.components.MainNavHost
import linkletter.client.feature.main.navigation.MainNavigator
import linkletter.client.feature.main.navigation.rememberMainNavigator

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    MainContent(modifier = modifier)
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    navigator: MainNavigator = rememberMainNavigator(),
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = LinkletterTheme.colorScheme.background,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            MainNavHost(navigator)

            MainBottomBar(
                visible = navigator.shouldShowBottomBar(),
                currentTab = navigator.currentTab,
                onTabSelected = { navigator.navigate(it) },
            )
        }
    }
}

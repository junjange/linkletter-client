package linkletter.client.feature.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import linkletter.client.core.navigation.MainTabRoute
import linkletter.client.feature.setting.SettingScreen

fun NavController.navigateSetting(navOptions: NavOptions) {
    navigate(MainTabRoute.Setting, navOptions)
}

fun NavGraphBuilder.settingNavGraph() {
    composable<MainTabRoute.Setting> {
        SettingScreen()
    }
}

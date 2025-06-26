package linkletter.client.feature.main.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import linkletter.client.core.designsystem.theme.LinkletterTheme
import linkletter.client.feature.main.navigation.MainTab
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun BoxScope.MainBottomBar(
    visible: Boolean,
    currentTab: MainTab?,
    onTabSelected: (MainTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        modifier =
            Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding(),
        visible = visible,
        enter = fadeIn() + slideIn { IntOffset(0, it.height) },
        exit = fadeOut() + slideOut { IntOffset(0, it.height) },
    ) {
        Surface(
            modifier = modifier,
            border = BorderStroke(1.dp, LinkletterTheme.colorScheme.borderColor),
        ) {
            NavigationBar(
                containerColor = LinkletterTheme.colorScheme.surface,
            ) {
                MainTab.entries.forEach { tab ->
                    MainBottomBarItem(
                        tab = tab,
                        selected = tab == currentTab,
                        onClick = { onTabSelected(tab) },
                    )
                }
            }
        }
    }
}

@Composable
private fun RowScope.MainBottomBarItem(
    tab: MainTab,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val color =
        if (selected) {
            LinkletterTheme.colorScheme.selectedIconColor
        } else {
            LinkletterTheme.colorScheme.unselectedIconColor
        }

    NavigationBarItem(
        icon = {
            Icon(
                painter = painterResource(tab.iconResId),
                contentDescription = tab.contentDescription,
                tint = color,
            )
        },
        label = {
            Text(
                text = tab.contentDescription,
                style =
                    LinkletterTheme.typography.labelSmallM.copy(
                        color = color,
                    ),
            )
        },
        modifier = modifier,
        selected = false,
        onClick = onClick,
        interactionSource = MutableInteractionSource(),
    )
}

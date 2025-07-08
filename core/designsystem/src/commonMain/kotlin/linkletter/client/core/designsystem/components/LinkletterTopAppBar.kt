package linkletter.client.core.designsystem.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import linkletter.client.core.designsystem.theme.LinkletterTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinkletterTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit) = {},
    actions: @Composable (RowScope.() -> Unit) = {},
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style =
                    LinkletterTheme.typography.titleLargeB.copy(
                        color = LinkletterTheme.colorScheme.onSurface,
                    ),
            )
        },
        modifier =
            modifier
                .fillMaxWidth()
                .padding(2.dp),
        windowInsets = TopAppBarDefaults.windowInsets,
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = LinkletterTheme.colorScheme.background,
                titleContentColor = LinkletterTheme.colorScheme.background,
                navigationIconContentColor = LinkletterTheme.colorScheme.background,
                actionIconContentColor = LinkletterTheme.colorScheme.background,
            ),
        navigationIcon = navigationIcon,
        actions = actions,
    )
}

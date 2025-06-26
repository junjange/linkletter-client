package linkletter.client.feature.home.componets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import linkletter.client.core.designsystem.theme.LinkletterTheme
import linkletter_client.core.designsystem.generated.resources.Res
import linkletter_client.core.designsystem.generated.resources.blog_add_fab_title
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ExpandableActionButton(
    modifier: Modifier = Modifier,
    isFabExpanded: Boolean,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        modifier = modifier,
        containerColor = LinkletterTheme.colorScheme.primary,
        shape = CircleShape,
        onClick = onClick,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = LinkletterTheme.colorScheme.onPrimary,
            )

            if (isFabExpanded) Spacer(modifier = Modifier.width(2.dp))

            AnimatedVisibility(visible = isFabExpanded) {
                Text(
                    text = stringResource(Res.string.blog_add_fab_title),
                    style = LinkletterTheme.typography.titleMediumB,
                    color = LinkletterTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}

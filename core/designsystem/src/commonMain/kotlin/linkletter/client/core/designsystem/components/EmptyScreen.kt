package linkletter.client.core.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import linkletter.client.core.designsystem.theme.Gray500
import linkletter.client.core.designsystem.theme.LinkletterTheme
import linkletter_client.core.designsystem.generated.resources.Res
import linkletter_client.core.designsystem.generated.resources.ic_empty
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun EmptyScreen(
    title: String,
    subTitle: String,
    modifier: Modifier = Modifier,
    imageRes: DrawableResource = Res.drawable.ic_empty,
    content: @Composable () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
        )

        Text(
            text = title,
            style = LinkletterTheme.typography.titleLargeB,
            color = LinkletterTheme.colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = subTitle,
            style = LinkletterTheme.typography.bodyMediumR,
            color = Gray500,
        )

        Spacer(modifier = Modifier.height(4.dp))

        content()
    }
}

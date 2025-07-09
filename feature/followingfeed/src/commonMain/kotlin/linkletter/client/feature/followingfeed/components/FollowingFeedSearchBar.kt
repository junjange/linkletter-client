package linkletter.client.feature.followingfeed.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import linkletter.client.core.designsystem.theme.LinkletterTheme
import linkletter_client.feature.followingfeed.generated.resources.Res
import linkletter_client.feature.followingfeed.generated.resources.hint_search
import linkletter_client.feature.followingfeed.generated.resources.ic_cancel
import linkletter_client.feature.followingfeed.generated.resources.ic_search
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun FollowingFeedSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    focusManager: FocusManager,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = {
            Text(
                text = stringResource(Res.string.hint_search),
                style = LinkletterTheme.typography.titleMediumR.copy(color = Color.Gray),
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(Res.drawable.ic_search),
                contentDescription = null,
                tint = LinkletterTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = {
                    onQueryChange("")
                }) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_cancel),
                        contentDescription = null,
                        tint = LinkletterTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions =
            KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                },
            ),
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors =
            TextFieldDefaults.colors(
                focusedContainerColor = LinkletterTheme.colorScheme.placeholderColor,
                unfocusedContainerColor = LinkletterTheme.colorScheme.placeholderColor,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = LinkletterTheme.colorScheme.onSurface,
            ),
    )
}

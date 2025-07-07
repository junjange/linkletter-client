package linkletter.client.feature.addblog.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import linkletter.client.core.designsystem.theme.LinkletterTheme
import linkletter_client.feature.addblog.generated.resources.Res
import linkletter_client.feature.addblog.generated.resources.hint_blog_add_search
import linkletter_client.feature.addblog.generated.resources.ic_arrow_back_ios_new
import linkletter_client.feature.addblog.generated.resources.ic_cancel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun AddBlogSearchBar(
    focusManager: FocusManager,
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    var query by rememberSaveable { mutableStateOf("") }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        BackButton(onBackClick = onBackClick)

        SearchTextField(
            query = query,
            onQueryChange = { query = it },
            onSearch = onSearch,
            focusManager = focusManager,
            modifier = modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun BackButton(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onBackClick,
        modifier = modifier,
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_arrow_back_ios_new),
            contentDescription = "뒤로 가기",
            tint = Color.Gray,
        )
    }
}

@Composable
private fun SearchTextField(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    focusManager: FocusManager,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        textStyle = LinkletterTheme.typography.titleMediumR.copy(color = LinkletterTheme.colorScheme.onSurface),
        placeholder = {
            SearchPlaceholder()
        },
        trailingIcon = {
            SearchTrailingIcon(
                query = query,
                onClear = { onQueryChange("") },
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions =
            KeyboardActions(
                onDone = {
                    if (query.isNotBlank()) {
                        onSearch(query)
                        focusManager.clearFocus()
                    }
                },
            ),
        singleLine = true,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = createTextFieldColors(),
    )
}

@Composable
private fun SearchPlaceholder(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(Res.string.hint_blog_add_search),
        style = LinkletterTheme.typography.titleMediumR.copy(color = Color.Gray),
        modifier = modifier,
    )
}

@Composable
private fun SearchTrailingIcon(
    query: String,
    onClear: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (query.isNotEmpty()) {
        IconButton(
            onClick = onClear,
            modifier = modifier,
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_cancel),
                contentDescription = "검색어 지우기",
                tint = Color.Gray,
            )
        }
    }
}

@Composable
private fun createTextFieldColors() =
    TextFieldDefaults.colors(
        focusedContainerColor = LinkletterTheme.colorScheme.placeholderColor,
        unfocusedContainerColor = LinkletterTheme.colorScheme.placeholderColor,
        disabledContainerColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        cursorColor = LinkletterTheme.colorScheme.onSurface,
    )

package linkletter.client.feature.blogfollow.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import linkletter.client.core.designsystem.components.NetworkImage
import linkletter.client.core.designsystem.theme.Gray500
import linkletter.client.core.designsystem.theme.LinkletterTheme
import linkletter.client.feature.blogfollow.model.BlogFollowUiModel

@Composable
internal fun BlogFollowListItem(
    blogFollowUiModel: BlogFollowUiModel,
    onClick: (BlogFollowUiModel) -> Unit,
    onFollow: (BlogFollowUiModel) -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable { onClick(blogFollowUiModel) }
                .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        NetworkImage(
            imageUrl = blogFollowUiModel.imageUrl,
            contentDescription = null,
            modifier =
                Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp)),
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = blogFollowUiModel.name,
                style = LinkletterTheme.typography.titleMediumR.copy(color = LinkletterTheme.colorScheme.onSurface),
            )

            Text(
                text = blogFollowUiModel.link,
                style = LinkletterTheme.typography.titleSmallR.copy(color = Gray500),
            )
        }
        IconButton(onClick = { onFollow(blogFollowUiModel) }) {
            Icon(
                imageVector = if (blogFollowUiModel.isFollowed) Icons.Default.Check else Icons.Default.Add,
                tint = if (blogFollowUiModel.isFollowed) LinkletterTheme.colorScheme.primarySurface else LinkletterTheme.colorScheme.onSurface,
                contentDescription = null,
            )
        }
    }
}

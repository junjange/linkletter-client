package linkletter.client.feature.blogfollow.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import linkletter.client.core.designsystem.components.NetworkImage
import linkletter.client.core.designsystem.theme.Gray500
import linkletter.client.core.designsystem.theme.LinkletterTheme
import linkletter.client.feature.blogfollow.model.BlogFollowState.BlogFollow

@Composable
internal fun BlogResultCard(
    blogFollow: BlogFollow,
    showPlaceholder: Boolean,
    modifier: Modifier = Modifier,
    onBlogClick: (String) -> Unit,
    onBlogFollow: () -> Unit,
) {
    val shimmerModifier =
        if (showPlaceholder) {
            Modifier
                .clip(RoundedCornerShape(10.dp))
                .width(200.dp)
                .background(color = LinkletterTheme.colorScheme.placeholderColor)
        } else {
            Modifier
        }
    Surface(
        modifier = modifier.padding(horizontal = 16.dp),
        color = LinkletterTheme.colorScheme.surface,
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 2.dp,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clickable { onBlogClick(blogFollow.blog.url) }
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            NetworkImage(
                imageUrl = blogFollow.blog.author.imageUrl,
                contentDescription = "블로그 작성자 프로필 이미지",
                modifier =
                    Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .then(shimmerModifier),
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = blogFollow.blog.name,
                    style = LinkletterTheme.typography.titleMediumR.copy(color = LinkletterTheme.colorScheme.onSurface),
                    modifier = Modifier.then(shimmerModifier),
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = blogFollow.blog.url,
                    style = LinkletterTheme.typography.titleSmallR.copy(color = Gray500),
                    modifier = Modifier.then(shimmerModifier),
                )
            }
            IconButton(
                onClick = onBlogFollow,
            ) {
                Icon(
                    imageVector = if (blogFollow.isFollowed) Icons.Default.Check else Icons.Default.Add,
                    tint =
                        if (blogFollow.isFollowed) {
                            LinkletterTheme.colorScheme.primarySurface
                        } else {
                            LinkletterTheme.colorScheme.onSurface
                        },
                    contentDescription = if (blogFollow.isFollowed) "언팔로우" else "팔로우",
                )
            }
        }
    }
}

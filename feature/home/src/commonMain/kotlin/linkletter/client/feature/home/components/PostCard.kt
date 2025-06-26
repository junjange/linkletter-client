package linkletter.client.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import linkletter.client.core.designsystem.components.NetworkImage
import linkletter.client.core.designsystem.theme.Gray500
import linkletter.client.core.designsystem.theme.LinkletterTheme
import linkletter.client.core.model.Author
import linkletter.client.core.model.Post
import linkletter_client.core.designsystem.generated.resources.Res
import linkletter_client.core.designsystem.generated.resources.by
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun PostCard(
    post: Post,
    showPlaceholder: Boolean,
    modifier: Modifier = Modifier,
    onClick: (link: String) -> Unit = {},
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
        color = LinkletterTheme.colorScheme.surface,
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 2.dp,
        modifier = modifier.fillMaxWidth(),
        onClick = { onClick(post.link) },
    ) {
        Column {
            ThumbnailImage(
                thumbnailUrl = post.thumbnailUrl,
                shimmerModifier = shimmerModifier,
            )

            Spacer(modifier = Modifier.height(12.dp))

            PostContent(
                post = post,
                shimmerModifier = shimmerModifier,
            )
        }
    }
}

@Composable
private fun ThumbnailImage(
    thumbnailUrl: String?,
    shimmerModifier: Modifier,
    modifier: Modifier = Modifier,
) {
    thumbnailUrl?.let {
        NetworkImage(
            imageUrl = thumbnailUrl,
            contentDescription = "썸네일",
            modifier =
                modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .then(shimmerModifier),
        )
    }
}

@Composable
private fun PostContent(
    post: Post,
    shimmerModifier: Modifier,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Text(
            text = post.title,
            style = LinkletterTheme.typography.titleLargeB,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = LinkletterTheme.colorScheme.onSurface,
            modifier = shimmerModifier,
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = post.description,
            style = LinkletterTheme.typography.bodyMediumR,
            maxLines = if (post.thumbnailUrl == null) 5 else 3,
            overflow = TextOverflow.Ellipsis,
            color = LinkletterTheme.colorScheme.onSurface,
            modifier = shimmerModifier,
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = post.pubDate,
            style = LinkletterTheme.typography.labelMediumR,
            color = Gray500,
            modifier = shimmerModifier,
        )

        Spacer(modifier = Modifier.height(12.dp))

        AuthorRow(
            author = post.author,
            shimmerModifier = shimmerModifier,
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun AuthorRow(
    author: Author,
    shimmerModifier: Modifier,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NetworkImage(
            imageUrl = author.imageUrl,
            contentDescription = "작성자 이미지",
            modifier = Modifier.size(20.dp).clip(CircleShape).then(shimmerModifier),
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = stringResource(Res.string.by),
            style = LinkletterTheme.typography.labelMediumR,
            color = Gray500,
        )
        Text(
            text = author.name,
            style = LinkletterTheme.typography.labelMediumR,
            color = LinkletterTheme.colorScheme.onSurface,
            modifier = shimmerModifier,
        )
    }
}

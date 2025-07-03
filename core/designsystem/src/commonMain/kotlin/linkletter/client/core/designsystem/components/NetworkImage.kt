package linkletter.client.core.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import linkletter_client.core.designsystem.generated.resources.Res
import linkletter_client.core.designsystem.generated.resources.ic_blue_owl
import org.jetbrains.compose.resources.painterResource

@Composable
fun NetworkImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    placeholder: Painter? = null,
    contentScale: ContentScale = ContentScale.Crop,
    contentDescription: String? = null,
) {
    if (imageUrl == null) {
        Image(
            painter = painterResource(Res.drawable.ic_blue_owl),
            modifier = modifier,
            contentScale = contentScale,
            contentDescription = contentDescription,
        )
    } else {
        AsyncImage(
            model =
                ImageRequest
                    .Builder(LocalPlatformContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
            modifier = modifier,
            placeholder = placeholder,
            contentScale = contentScale,
            contentDescription = contentDescription,
        )
    }
}

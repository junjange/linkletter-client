package linkletter.client

import androidx.compose.runtime.*
import linkletter.client.core.designsystem.theme.LinkletterTheme
import linkletter.client.feature.main.MainScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    LinkletterTheme {
        MainScreen()
    }
}

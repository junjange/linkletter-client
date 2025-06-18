package linkletter.client

import androidx.compose.runtime.*
import linkletter.client.core.designsystem.theme.LinkletterTheme
import linkletter.client.feature.bookmark.di.featureBookmarkModule
import linkletter.client.feature.home.di.featureHomeModule
import linkletter.client.feature.main.MainScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.KoinApplication
import org.koin.dsl.KoinAppDeclaration

@Composable
@Preview
fun App() {
    LinkletterTheme {
        MainScreen()
    }
}

fun linkletterAppDeclaration(additionalDeclaration: KoinApplication.() -> Unit = {}): KoinAppDeclaration =
    {
        val featureModules =
            listOf(
                featureHomeModule,
                featureBookmarkModule,
            )
        modules(featureModules)
        additionalDeclaration()
    }

package linkletter.client

import androidx.compose.runtime.Composable
import linkletter.client.core.data.di.coreDataModule
import linkletter.client.core.designsystem.theme.LinkletterTheme
import linkletter.client.core.domain.di.coreDomainModule
import linkletter.client.core.network.di.coreNetworkModule
import linkletter.client.feature.bookmark.di.featureBookmarkModule
import linkletter.client.feature.home.di.featureHomeModule
import linkletter.client.feature.main.MainScreen
import org.koin.core.KoinApplication
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

@Composable
fun App() {
    LinkletterTheme {
        MainScreen()
    }
}

internal val appModule =
    module {
        includes(
            coreNetworkModule,
        )

        includes(
            coreDataModule,
        )

        includes(
            coreDomainModule,
        )

        includes(
            featureHomeModule,
            featureBookmarkModule,
        )
    }

internal fun linkletterAppDeclaration(additionalDeclaration: KoinApplication.() -> Unit = {}): KoinAppDeclaration =
    {
        modules(appModule)
        additionalDeclaration()
    }

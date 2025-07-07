package linkletter.client

import androidx.compose.runtime.Composable
import linkletter.client.core.data.di.coreDataModule
import linkletter.client.core.database.di.coreDatabaseModule
import linkletter.client.core.designsystem.theme.LinkletterTheme
import linkletter.client.core.domain.di.coreDomainModule
import linkletter.client.core.network.di.coreNetworkModule
import linkletter.client.feature.addblog.di.featureAddBlogModule
import linkletter.client.feature.followingfeed.di.featureFollowingFeedModule
import linkletter.client.feature.main.MainScreen
import linkletter.client.feature.mybloggers.di.featureMyBloggersModule
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
            coreDatabaseModule,
        )

        includes(
            coreDataModule,
        )

        includes(
            coreDomainModule,
        )

        includes(
            featureFollowingFeedModule,
            featureMyBloggersModule,
            featureAddBlogModule,
        )
    }

internal fun linkletterAppDeclaration(additionalDeclaration: KoinApplication.() -> Unit = {}): KoinAppDeclaration =
    {
        modules(appModule)
        additionalDeclaration()
    }

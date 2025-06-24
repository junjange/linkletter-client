package linkletter.client.core.network.di

import linkletter.client.core.network.LinkletterNetwork
import org.koin.dsl.module

val coreNetworkModule =
    module {
        single { LinkletterNetwork() }
    }

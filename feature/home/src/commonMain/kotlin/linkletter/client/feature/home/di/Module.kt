package linkletter.client.feature.home.di

import linkletter.client.feature.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureHomeModule =
    module {
        viewModelOf(::HomeViewModel)
    }

package linkletter.client.feature.mybloggers.di

import linkletter.client.feature.mybloggers.MyBloggersViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureMyBloggersModule =
    module {
        viewModelOf(::MyBloggersViewModel)
    }

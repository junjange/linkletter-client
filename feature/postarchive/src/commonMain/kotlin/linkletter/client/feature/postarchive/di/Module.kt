package linkletter.client.feature.postarchive.di

import linkletter.client.feature.postarchive.PostArchiveViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featurePostArchiveModule =
    module {
        viewModelOf(::PostArchiveViewModel)
    }

package linkletter.client.feature.blogadd.di

import linkletter.client.feature.blogadd.BlogAddViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureBlogAddModule =
    module {
        viewModelOf(::BlogAddViewModel)
    }

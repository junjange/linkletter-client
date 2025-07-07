package linkletter.client.feature.addblog.di

import linkletter.client.feature.addblog.AddBlogViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureAddBlogModule =
    module {
        viewModelOf(::AddBlogViewModel)
    }

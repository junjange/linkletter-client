package linkletter.client.feature.blogfollow.di

import linkletter.client.feature.blogfollow.BlogFollowViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureBlogFollowModule =
    module {
        viewModelOf(::BlogFollowViewModel)
    }

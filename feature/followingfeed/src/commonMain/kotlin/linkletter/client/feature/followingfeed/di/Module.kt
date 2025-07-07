package linkletter.client.feature.followingfeed.di

import linkletter.client.feature.followingfeed.FollowingFeedViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureFollowingFeedModule =
    module {
        viewModelOf(::FollowingFeedViewModel)
    }

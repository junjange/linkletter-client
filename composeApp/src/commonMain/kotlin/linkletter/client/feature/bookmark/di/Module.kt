package linkletter.client.feature.bookmark.di

import linkletter.client.feature.bookmark.BookmarkViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureBookmarkModule =
    module {
        viewModelOf(::BookmarkViewModel)
    }

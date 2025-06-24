package linkletter.client.core.data.di

import linkletter.client.core.data.repository.DefaultPostsRepository
import linkletter.client.core.data.repository.DefaultRssUrlRepository
import linkletter.client.core.data.source.DefaultRssDataSource
import linkletter.client.core.data.source.DefaultRssUrlDataSource
import linkletter.client.core.data.source.RssDataSource
import linkletter.client.core.data.source.RssUrlDataSource
import linkletter.client.core.domain.repository.PostsRepository
import linkletter.client.core.domain.repository.RssUrlRepository
import org.koin.dsl.module

val coreDataModule =
    module {
        single<RssUrlDataSource> { DefaultRssUrlDataSource(get()) }
        single<RssDataSource> { (DefaultRssDataSource(get())) }
        single<PostsRepository> { DefaultPostsRepository(get(), get()) }
        single<RssUrlRepository> { DefaultRssUrlRepository(get()) }
    }

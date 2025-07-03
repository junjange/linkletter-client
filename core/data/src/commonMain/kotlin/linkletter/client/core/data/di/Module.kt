package linkletter.client.core.data.di

import linkletter.client.core.data.repository.DefaultBlogRepository
import linkletter.client.core.data.source.BlogInfoDataSource
import linkletter.client.core.data.source.DefaultBlogInfoInfoDataSource
import linkletter.client.core.data.source.DefaultRssDataSource
import linkletter.client.core.data.source.DefaultRssUrlDataSource
import linkletter.client.core.data.source.RssDataSource
import linkletter.client.core.data.source.RssUrlDataSource
import linkletter.client.core.domain.repository.BlogRepository
import org.koin.dsl.module

val coreDataModule =
    module {
        // source
        single<BlogInfoDataSource> { DefaultBlogInfoInfoDataSource(get()) }
        single<RssUrlDataSource> { DefaultRssUrlDataSource(get()) }
        single<RssDataSource> { (DefaultRssDataSource(get())) }

        // repository
        single<BlogRepository> { DefaultBlogRepository(get(), get(), get()) }
    }

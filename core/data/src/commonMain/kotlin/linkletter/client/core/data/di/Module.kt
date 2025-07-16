package linkletter.client.core.data.di

import linkletter.client.core.data.repository.DefaultAlarmSettingRepository
import linkletter.client.core.data.repository.DefaultBlogRepository
import linkletter.client.core.data.repository.DefaultLatestPostRepository
import linkletter.client.core.data.source.AlarmSettingDataSource
import linkletter.client.core.data.source.BlogInfoDataSource
import linkletter.client.core.data.source.DefaultAlarmSettingDataSource
import linkletter.client.core.data.source.DefaultBlogInfoInfoDataSource
import linkletter.client.core.data.source.DefaultLatestPostDataSource
import linkletter.client.core.data.source.DefaultRssDataSource
import linkletter.client.core.data.source.DefaultRssUrlDataSource
import linkletter.client.core.data.source.LatestPostDataSource
import linkletter.client.core.data.source.RssDataSource
import linkletter.client.core.data.source.RssUrlDataSource
import linkletter.client.core.domain.repository.AlarmSettingRepository
import linkletter.client.core.domain.repository.BlogRepository
import linkletter.client.core.domain.repository.LatestPostRepository
import org.koin.dsl.module

val coreDataModule =
    module {
        // source
        single<BlogInfoDataSource> { DefaultBlogInfoInfoDataSource(get()) }
        single<RssUrlDataSource> { DefaultRssUrlDataSource(get()) }
        single<RssDataSource> { (DefaultRssDataSource(get())) }
        single<AlarmSettingDataSource> { (DefaultAlarmSettingDataSource(get())) }
        single<LatestPostDataSource> { (DefaultLatestPostDataSource(get())) }

        // repository
        single<BlogRepository> { DefaultBlogRepository(get(), get(), get()) }
        single<AlarmSettingRepository> { DefaultAlarmSettingRepository(get()) }
        single<LatestPostRepository> { (DefaultLatestPostRepository(get())) }
    }

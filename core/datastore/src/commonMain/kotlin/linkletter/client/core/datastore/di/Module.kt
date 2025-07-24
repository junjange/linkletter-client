package linkletter.client.core.datastore.di

import linkletter.client.core.datastore.AlarmSettingPreferences
import linkletter.client.core.datastore.DataStoreProvider
import linkletter.client.core.datastore.LatestPostPreferences
import org.koin.dsl.module

val coreDataStoreModule =
    module {
        single { LatestPostPreferences(get()) }
        single { AlarmSettingPreferences(get()) }
        single { DataStoreProvider() }
    }

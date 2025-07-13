package linkletter.client.core.datastore.di

import linkletter.client.core.datastore.AlarmSettingPreferences
import linkletter.client.core.datastore.DataStoreProvider
import org.koin.dsl.module

val coreDataStoreModule =
    module {
        single { AlarmSettingPreferences(get()) }
        single { DataStoreProvider() }
    }

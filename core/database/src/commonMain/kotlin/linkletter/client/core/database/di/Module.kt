package linkletter.client.core.database.di

import linkletter.client.core.database.LinkletterDatabase
import linkletter.client.core.database.getRoomDatabase
import org.koin.dsl.module

val coreDatabaseModule =
    module {
        single {
            getRoomDatabase()
        }

        single { get<LinkletterDatabase>().blogInfoDao() }
    }

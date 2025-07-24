package linkletter.client.core.notification.di

import linkletter.client.core.domain.repository.NotificationScheduler
import linkletter.client.core.notification.provideNotificationScheduler
import org.koin.core.module.Module
import org.koin.dsl.module

val coreNotificationModule: Module =
    module {
        single<NotificationScheduler> {
            provideNotificationScheduler()
        }
    }

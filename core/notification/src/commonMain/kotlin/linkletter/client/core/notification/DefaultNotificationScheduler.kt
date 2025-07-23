package linkletter.client.core.notification

import linkletter.client.core.domain.repository.NotificationScheduler
import kotlin.time.Clock.System
import kotlin.time.ExperimentalTime

expect class DefaultNotificationScheduler {
    @OptIn(ExperimentalTime::class)
    suspend fun scheduleDailyNotification(id: String = "notification_${System.now().epochSeconds}")

    suspend fun cancelAllNotifications()
}

expect fun provideNotificationScheduler(): NotificationScheduler

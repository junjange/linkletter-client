package linkletter.client.core.domain.repository

interface NotificationScheduler {
    suspend fun scheduleDailyNotification(id: String)

    suspend fun cancelAllNotifications()
}

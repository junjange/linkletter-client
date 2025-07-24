package linkletter.client.core.notification

import linkletter.client.core.domain.repository.NotificationScheduler
import platform.Foundation.NSDateComponents
import platform.UserNotifications.UNCalendarNotificationTrigger
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNNotificationSound
import platform.UserNotifications.UNUserNotificationCenter

actual class DefaultNotificationScheduler : NotificationScheduler {
    private val center = UNUserNotificationCenter.currentNotificationCenter()

    actual override suspend fun scheduleDailyNotification(id: String) {
        val comps =
            NSDateComponents().apply {
                hour = NOTIFICATION_HOUR
                minute = NOTIFICATION_MINUTE
            }
        val trigger = UNCalendarNotificationTrigger.triggerWithDateMatchingComponents(comps, true)

        val content =
            UNMutableNotificationContent().apply {
                setTitle(NOTIFICATION_TITLE)
                setBody(NOTIFICATION_BODY)
                setSound(UNNotificationSound.defaultSound())
            }

        val req = UNNotificationRequest.requestWithIdentifier(id, content, trigger)

        center.addNotificationRequest(req, null)
    }

    actual override suspend fun cancelAllNotifications() {
        center.removeAllPendingNotificationRequests()
        center.removeAllDeliveredNotifications()
    }

    companion object {
        private const val NOTIFICATION_HOUR = 8L
        private const val NOTIFICATION_MINUTE = 0L

        const val NOTIFICATION_TITLE = "새 글 확인"
        private const val NOTIFICATION_BODY = "팔로우 중인 블로그에 새 글이 있는지 한 번 살펴보세요"
    }
}

actual fun provideNotificationScheduler(): NotificationScheduler = DefaultNotificationScheduler()

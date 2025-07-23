package linkletter.client.core.notification

import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.datetime.LocalTime
import platform.Foundation.NSDateComponents
import platform.Foundation.NSLog
import platform.UserNotifications.*
import kotlin.coroutines.resume
import kotlin.time.ExperimentalTime

actual class DefaultNotificationScheduler {
    private val center: UNUserNotificationCenter =
        UNUserNotificationCenter.currentNotificationCenter()

    @OptIn(ExperimentalTime::class)
    actual suspend fun scheduleDailyNotification(
        time: LocalTime,
        title: String,
        message: String,
        id: String,
    ) {
        val content =
            UNMutableNotificationContent().apply {
                this.title = title
                this.body = message
                this.sound = UNNotificationSound.defaultSound()
            }

        val dateComponents =
            NSDateComponents().apply {
                hour = time.hour.toLong()
                minute = time.minute.toLong()
                second = time.second.toLong()
            }

        val trigger =
            UNCalendarNotificationTrigger.triggerWithDateMatchingComponents(
                dateComponents,
                repeats = true,
            )

        val request =
            UNNotificationRequest.requestWithIdentifier(
                id,
                content,
                trigger,
            )

        center.addNotificationRequest(request) { error ->
            if (error != null) {
                NSLog("[NotificationScheduler] scheduling error: %{public}@", error)
            }
        }
    }

    actual suspend fun cancelNotification(id: String) {
        center.removePendingNotificationRequests(listOf(id))
    }

    actual suspend fun cancelAllNotifications() {
        center.removeAllPendingNotificationRequests()
    }

    actual suspend fun requestPermission(): Boolean =
        suspendCancellableCoroutine { cont ->
            center.requestAuthorizationWithOptions(
                authorizationOptions =
                    UNAuthorizationOptionAlert or
                        UNAuthorizationOptionSound or
                        UNAuthorizationOptionBadge,
            ) { granted, error ->
                if (error != null) {
                    NSLog("[NotificationScheduler] permission error: %{public}@", error)
                }
                cont.resume(granted)
            }
        }
}

actual fun provideNotificationScheduler(): DefaultNotificationScheduler = DefaultNotificationScheduler()

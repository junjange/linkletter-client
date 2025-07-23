package linkletter.client.core.domain.usecase

import kotlinx.datetime.Clock.System
import linkletter.client.core.domain.repository.AlarmSettingRepository
import linkletter.client.core.domain.repository.NotificationScheduler

internal class DefaultSetAlarmEnabledUseCase(
    private val repository: AlarmSettingRepository,
    private val notificationScheduler: NotificationScheduler,
) : SetAlarmEnabledUseCase {
    override suspend fun invoke(enabled: Boolean) {
        repository.setAlarmEnabled(enabled = enabled)
        if (enabled) {
            notificationScheduler.scheduleDailyNotification(id = "notification_${System.now().epochSeconds}")
        } else {
            notificationScheduler.cancelAllNotifications()
        }
    }
}

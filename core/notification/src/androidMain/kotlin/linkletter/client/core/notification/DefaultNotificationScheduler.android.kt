package linkletter.client.core.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import kotlinx.datetime.LocalTime
import linkletter.client.core.domain.repository.NotificationScheduler
import org.koin.java.KoinJavaComponent
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.time.ExperimentalTime

actual class DefaultNotificationScheduler(
    private val context: Context,
) : NotificationScheduler {
    private val alarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    @OptIn(ExperimentalTime::class)
    actual override suspend fun scheduleDailyNotification(id: String) {
        val intent = AlarmReceiver.newIntent(context)
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                id.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        val triggerAtMillis = calculateNextTrigger(LocalTime(8, 0))

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent,
        )
    }

    fun calculateNextTrigger(targetTime: LocalTime): Long {
        val zone = ZoneId.systemDefault()
        val now = ZonedDateTime.now(zone)
        var next =
            now
                .withHour(targetTime.hour)
                .withMinute(targetTime.minute)
                .withSecond(0)
                .withNano(0)

        if (next <= now) next = next.plusDays(1)
        return next.toInstant().toEpochMilli()
    }

    actual override suspend fun cancelAllNotifications() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            alarmManager.cancelAll()
        }
    }
}

actual fun provideNotificationScheduler(): NotificationScheduler {
    val appContext: Context = KoinJavaComponent.getKoin().get()
    return DefaultNotificationScheduler(appContext)
}

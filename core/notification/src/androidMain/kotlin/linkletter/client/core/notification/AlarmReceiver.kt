package linkletter.client.core.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import linkletter.client.core.domain.usecase.CheckNewPostUseCase
import linkletter.client.core.domain.usecase.GetAllBlogInfosUseCase
import org.koin.java.KoinJavaComponent.inject

class AlarmReceiver : BroadcastReceiver() {
    private lateinit var notificationManager: NotificationManager
    private val getAllBlogInfosUseCase: GetAllBlogInfosUseCase by inject(GetAllBlogInfosUseCase::class.java)
    private val checkNewPostUseCase: CheckNewPostUseCase by inject(CheckNewPostUseCase::class.java)

    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()

        val pending = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val blogs = getAllBlogInfosUseCase().firstOrNull().orEmpty()
                var hasNew = false
                blogs.forEach { blog ->
                    val newLink = checkNewPostUseCase(blog.url)
                    newLink?.let {
                        hasNew = true
                        sendNotification(
                            context = context,
                            notificationId = blog.url.hashCode(),
                            title = "새 글이 등록되었습니다",
                            message = "${blog.name}에 새로운 글이 올라왔어요!",
                        )
                    }
                }

                if (!hasNew) {
                    sendNotification(
                        context = context,
                        notificationId = -1,
                        title = "새 글이 없습니다",
                        message = "팔로우한 블로그 중에 새로운 글이 없어요",
                    )
                }
            } finally {
                pending.finish()
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH,
                )
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(
                notificationChannel,
            )
        }
    }

    private fun sendNotification(
        context: Context,
        notificationId: Int,
        title: String,
        message: String,
    ) {
        val pendingIntent = mainActivityPendingIntent(context = context, requestId = notificationId)

        val notification =
            NotificationCompat
                .Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_main)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

        notificationManager.notify(notificationId, notification)
    }

    private fun mainActivityPendingIntent(
        context: Context,
        requestId: Int,
    ): PendingIntent {
        val intent =
            Intent("linkletter.intent.action.OPEN_MAIN").apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        return PendingIntent.getActivity(
            context,
            requestId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }

    companion object {
        private const val CHANNEL_ID = "linkletter_id"
        private const val CHANNEL_NAME = "linkletter_notifications"

        fun newIntent(context: Context): Intent = Intent(context, AlarmReceiver::class.java)
    }
}

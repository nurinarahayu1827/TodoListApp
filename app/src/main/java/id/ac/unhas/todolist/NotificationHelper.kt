package id.ac.unhas.todolist.NotificationHelper

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import androidx.annotation.WorkerThread
import id.ac.unhas.todolist.R

class NotificationHelper(private val context: Context) {
    companion object {
        private const val CHANNEL_TODOS = "todos"
        private const val REQUEST_CONTENT = 1
        private const val REQUEST_BUBBLE = 2
    }
    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    fun setUpNotificationChannels() {
        if (notificationManager.getNotificationChannel(CHANNEL_TODOS) == null) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_TODOS,
                    context.getString(R.string,channel_todos),
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = context.getString(R.string.channel_todos_description)
                }
            )
        }
    }

    @WorkerThread
    fun showNotification(fromUser : Boolean) {
        val builder = Notification.Builder(context, CHANNEL_TODOS)
            .setBubbleMetadata(
                Notification.BubbleMetadata.Builder()
                    .setDesiredHeight(context.resources.getDimensionPixelSize(R.dimen.bubble_height))
                    .setIcon(Icon.createWithResource(context,R.drawable.ic_file_black_24dp))
                    .apply {
                        if (fromUser) {
                            setAutoExpandBubble(true)
                            setSuppressNotification(true)
                        }
                    }
                    .setIntent(
                        PendingIntent.getActivities(
                            context,
                            REQUEST_BUBBLE,
                            Intent(context.MainActivity::class.java)
                                .setAcction(Intent.ACTION_VIEW),
                            PendingIntent.FLAG_UPDATE_CURRENT
                        )
                    )
            )
    }

}

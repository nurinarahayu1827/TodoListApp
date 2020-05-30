package id.ac.unhas.todolist

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.LocusId
import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.WorkerThread
import id.ac.unhas.todolist.R

class NotificationHelper(private val context: Context) {
    companion object {
        private const val CHANNEL_TODOS = "todos"
        private const val REQUEST_CONTENT = 1
        private const val REQUEST_BUBBLE = 2
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    @RequiresApi(Build.VERSION_CODES.M)
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

    @RequiresApi(Build.VERSION_CODES.Q)
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
                            arrayOf(
                                Intent(context, MainActivity::class.java)
                                    .setAction(Intent.ACTION_VIEW)
                            ),
                            PendingIntent.FLAG_UPDATE_CURRENT
                        )
                    )
                    .build()
            )
            .setContentTitle("Todo")
            .setSmallIcon(R.drawable.ic_file_black_24dp)
            .setCategory(Notification.CATEGORY_STATUS)
            .setShowWhen(true)
        notificationManager.notify(0, builder.build())
    }
    fun dismissNotification(id: Long) {
        notificationManager.cancel(id.toInt())
    }
    fun canBubble():Boolean{
        val channel = notificationManager.getNotificationChannel(CHANNEL_TODOS)
        return notificationManager.areBubblesAllowed() && channel.canBubble()
    }

}

package ru.trinitydigital.radio.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import ru.trinitydigital.radio.BuildConfig
import ru.trinitydigital.radio.R
import ru.trinitydigital.radio.data.RadioService
import ru.trinitydigital.radio.data.enums.NotificationClickTypes

object NotificationUtils {

    private const val CHANNEL_ID = "CHANNEL_ID"
    const val EXTRA_ACTION = "EXTRA_ACTION"

    fun createNotification(context: Context): Notification? {
        createNotificationChannel(context)

        val notificationLayout = RemoteViews(BuildConfig.APPLICATION_ID, R.layout.view_notification)

        notificationLayout.setOnClickPendingIntent(
            R.id.imgPlay,
            getPendingIntentToService(context, NotificationClickTypes.PLAY)
        )
        notificationLayout.setOnClickPendingIntent(
            R.id.imgPrev,
            getPendingIntentToService(context, NotificationClickTypes.PREV)
        )
        notificationLayout.setOnClickPendingIntent(
            R.id.imgNext,
            getPendingIntentToService(context, NotificationClickTypes.NEXT)
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_sports_football_24)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        return builder.build()
    }

    private fun getPendingIntentToService(context: Context, type: NotificationClickTypes) =
        PendingIntent.getService(
            context,
            type.ordinal,
            Intent(context, RadioService::class.java).apply {
                action = type.name
            },
            0
        )

    private fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.channel_name)
            val descriptionText = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
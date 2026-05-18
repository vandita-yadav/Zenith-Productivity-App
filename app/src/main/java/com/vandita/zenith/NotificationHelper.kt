package com.vandita.zenith

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

const val CHANNEL_ID = "zenith_nudges"

fun createNotificationChannel(context: Context) {

    // Notification channels required for Android 8+
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        val channel = NotificationChannel(
            CHANNEL_ID,
            "Zenith Smart Nudges",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Smart focus reminders from Zenith"
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }
}

fun showFocusNotification(
    context: Context,
    appName: String,
    minutes: Long
) {

    val notification = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle("Zenith Focus Alert")
        .setContentText(
            "You've used $appName for $minutes minutes today. Time for a break?"
        )
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()

    NotificationManagerCompat.from(context)
        .notify(1, notification)
}
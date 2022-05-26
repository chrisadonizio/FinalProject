package com.example.finalproject

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

const val notificationId =1
const val channelID = "todo"
const val title = "To Do List"
const val message = "You have a task that should be completed today"

class Notification: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("is called","here")
        var builder = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_baseline_message_24)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
        }
    }
}
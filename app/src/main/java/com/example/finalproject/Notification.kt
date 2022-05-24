package com.example.finalproject

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

const val notificationID =1
const val channelID = "todo"
const val title = "Look Here"
const val message = "Test"

class Notification: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        var builder = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_baseline_message_24)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH).build()
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID,builder)
    }
}
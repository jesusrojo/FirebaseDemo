package com.jesusrojo.firebasedemo.messaging2.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.jesusrojo.firebasedemo.R
import com.jesusrojo.firebasedemo.messaging2.util.sendNotification

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        //Step 1.10 [Optional] remove toast
        //Toast.makeText(context, context.getText(R.string.eggs_ready), Toast.LENGTH_SHORT).show()

        //Step 1.9 add call to sendNotification
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.sendNotification(context.getText(R.string.eggs_ready).toString(), context)
    }

}
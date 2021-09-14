package com.jesusrojo.firebasedemo.messaging

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.jesusrojo.firebasedemo.R
import com.jesusrojo.firebasedemo.util.DebugHelp
import com.jesusrojo.firebasedemo.util.GoogleUtil

class MessagingHelp(
    private val activity: Activity,
    private val listener: (String) -> Unit) {

    private val myTag = javaClass.simpleName

    fun onCreate() {
        //checkForGooglePlayServices()
        createChannelNotifications()
        handleIntentMessagingOnCreate()
    }

    fun onResume() {
        checkGooglePlayServices()
    }

    private fun createChannelNotifications() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = activity.getString(R.string.default_notification_channel_id)
            val channelName = activity.getString(R.string.default_notification_channel_name)
            val notificationManager = activity.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(
                NotificationChannel(
                    channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW
                )
            )
        }
    }

    private fun handleIntentMessagingOnCreate() {
        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        activity.intent.extras?.let {
            for (key in it.keySet()) {
                val value = activity.intent.extras?.get(key)
                val msg = "Key: $key Value: $value"
                DebugHelp.l(myTag, msg)
                listener?.invoke(msg)
            }
        }
        // [END handle_data_extras]
    }

    fun getRegToken() {
        Firebase.messaging.token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
               DebugHelp.l(myTag, "Fetching FCM registration token failed "+ task.exception)
                return@OnCompleteListener
            }
            val token = task.result  // Get new FCM registration token
            val msg = activity.getString(R.string.msg_token_fmt, token)
            logAndToast(msg)
            //d9D6a1SEREGCXVPB_Mn24j:APA91bFKRQpGr6NrYLLZwY9kRJgpcm51wSlpowhm7prUHlppaHYW7WI2AY1yOukNN1KDZa2kCx24EGB9vUi8_exoI9Ko_WNOblLFyRRZsggEf-zQ7mTIhScUQaRd2b-cF-pga7jTIU5d
        })
    }

    internal fun subscribeToTopic() {
       DebugHelp.l(myTag, "subscribeToTopic")
        Firebase.messaging.subscribeToTopic("weather")
            .addOnCompleteListener { task ->
                var msg = activity.getString(R.string.msg_subscribed)
                if (!task.isSuccessful) {
                    msg = activity.getString(R.string.msg_subscribe_failed)
                }
                logAndToast(msg)
            }
    }

    private fun logAndToast(msg: String) {
       DebugHelp.l(myTag, msg)
        Toast.makeText(activity.applicationContext, msg, Toast.LENGTH_SHORT).show()
        listener?.invoke(msg)
    }

    private fun checkGooglePlayServices() {
        if (!GoogleUtil.isGooglePlayServicesAvailable(activity)) {
            GoogleUtil.downloadGooglePlayServices(activity)
        }
    }
}
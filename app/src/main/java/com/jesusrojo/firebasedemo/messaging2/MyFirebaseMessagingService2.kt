package com.jesusrojo.firebasedemo.messaging2

import android.app.NotificationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.jesusrojo.firebasedemo.messaging2.util.sendNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jesusrojo.firebasedemo.util.DebugHelp

class MyFirebaseMessagingService2: FirebaseMessagingService() {

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        DebugHelp.l(TAG, "onMessageReceived From: ${remoteMessage?.from}")

        //  Step 3.5 check messages for data
        // Check if message contains a data payload.
        remoteMessage?.data?.let {
            DebugHelp.l(TAG, "Message data payload: " + remoteMessage.data)
        }

        //  Step 3.6 check messages for notification and call sendNotification
        // Check if message contains a notification payload.
        remoteMessage?.notification?.let {
            DebugHelp.l(TAG, "Message Notification Body: ${it.body}")
           sendNotification(it.body!!) //commented this notification arrive
        }
    }
    // [END receive_message]

    // Step 3.2 log registration token
    // [START on_new_token]
    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        DebugHelp.l(TAG, "Refreshed token: $token")
//ems0NzYpRN-zOShw7u1NGm:APA91bEmrIoQsKlF0QnCdI4NaNLzEWVkRJ0_uQyluFmSyahLHrrFKlLookUjV0v-vQWRK2djVxvnce9D7vdTEjN8wWapqq8InF7cbFBVtNJbitu0I2RjBcySi9YE5IRdEWAkPUhkPYcO
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token)
    }
    // [END on_new_token]


    /**
     * Persist token to third-party servers.
     *
     * @param token The new token.
     */
    private fun sendRegistrationToServer(token: String?) {
        //  Implement this method to send token to your app server.
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private fun sendNotification(messageBody: String) {
        val notificationManager = ContextCompat.getSystemService(applicationContext, NotificationManager::class.java) as NotificationManager
        notificationManager.sendNotification(messageBody, applicationContext)
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService2"
    }
}
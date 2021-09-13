package com.jesusrojo.firebasedemo.messaging

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.OneTimeWorkRequest
import androidx.work.*
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jesusrojo.firebasedemo.util.DebugHelp

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val myTag: String = javaClass.simpleName

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        DebugHelp.l(myTag, "onMessageReceived #####################")
        var textMsg = "From: ${remoteMessage.from}"

        // Check if message contains a data payload.
        val dataReceived = remoteMessage.data
        if (dataReceived.isNotEmpty()) {
            textMsg += "Data $dataReceived"

//            if (/* Check if data needs to be processed by long running job TODO*/ true) {
//                scheduleJob()// For long-running tasks (10 seconds or more) use WorkManager
//            } else {
//                handleNow()// Handle message within 10 seconds
//            }
        }
        // Check if message contains a notification payload.
        remoteMessage.notification?.let { textMsg += "Body: ${it.body} ##" }
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

        //////////////////////////////////
        //// val messageReceived = dataReceived[INTENT_ACTION_SEND_MESSAGE_PARAM_KEY]!!
        val messageReceived = textMsg
        passMsgToActivity(messageReceived)
        //////////////////////////////////
    }

    private fun passMsgToActivity(message: String) {
        DebugHelp.l(myTag,  "passMsgToActivity $message")

        val intent = Intent().apply {
            action = INTENT_ACTION_SEND_MESSAGE
            putExtra(INTENT_ACTION_SEND_MESSAGE_PARAM_KEY, message)
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        DebugHelp.l(myTag,  "onNewToken: $token *****")

        Toast.makeText(this, "onNewToken $token", Toast.LENGTH_SHORT).show()
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token)
    }

//    /**
//     * Schedule async work using WorkManager.
//     */
//    private fun scheduleJob() {
//        val work = OneTimeWorkRequest.Builder(MyWorker::class.java).build()
//        WorkManager.getInstance(this).beginWith(work).enqueue()
//    }
//
//    /**
//     * Handle time allotted to BroadcastReceivers.
//     */
//    private fun handleNow() {
//        DebugHelp.l(myTag,  "Short lived task is done.")
//    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
        DebugHelp.l(myTag, "sendRegistrationTokenToServer($token)")

        val userId = "1" //todo userId, deviceId
        val data = Data.Builder()
            .putString("TOKEN_KEY", token)
            .putString("DEVICE_KEY", userId)
            .build()

        val workRequest: WorkRequest = OneTimeWorkRequestBuilder<SendTokenWorker>()
            .setInputData(data)
            .build()
        WorkManager.getInstance(applicationContext).enqueue(workRequest)
    }
////////////////////////////////
//    /**
//     * Create and show a simple notification containing the received FCM message.
//     *
//     * @param messageBody FCM message body received.
//     */
//    private fun sendNotification(messageBody: String) {
//        val intent = Intent(this, MessagingActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT)
//
//        val channelId = getString(R.string.default_notification_channel_id)
//        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//        val notificationBuilder = NotificationCompat.Builder(this, channelId)
//                .setSmallIcon(R.drawable.ic_stat_ic_notification)
//                .setContentTitle(getString(R.string.fcm_message))
//                .setContentText(messageBody)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent)
//
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        // Since android Oreo notification channel is needed.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(channelId,
//                    "Channel human readable title",
//                    NotificationManager.IMPORTANCE_DEFAULT)
//            notificationManager.createNotificationChannel(channel)
//        }
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
//    }

    companion object {
        const val INTENT_ACTION_SEND_MESSAGE = "INTENT_ACTION_SEND_MESSAGE"
        const val INTENT_ACTION_SEND_MESSAGE_PARAM_KEY = "message"
    }
}

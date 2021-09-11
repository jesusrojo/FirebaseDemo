package com.jesusrojo.firebasedemo.messaging


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager

import com.jesusrojo.firebasedemo.databinding.MessagingActivityBinding

class MessagingActivity : AppCompatActivity() {

    private lateinit var messagingHelp: MessagingHelp
    lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // UI
        val binding = MessagingActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.subscribeButton.setOnClickListener { subscribeToTopic() }
        binding.logTokenButton.setOnClickListener { getRegToken() }

        // MESSAGING
        messagingHelp = MessagingHelp(this){ textUi -> updateUi(binding, textUi) }
        messagingHelp.onCreate()

        initBroadCastReceiver()
    }

    override fun onResume() {
        super.onResume()
        messagingHelp.onResume()

        val filter = IntentFilter(MyFirebaseMessagingService.INTENT_ACTION_SEND_MESSAGE)
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
    }


    private fun initBroadCastReceiver() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val msg =
                    intent.getStringExtra(MyFirebaseMessagingService.INTENT_ACTION_SEND_MESSAGE_PARAM_KEY)
                if (msg != null && msg.isNotEmpty()){
                    showAlertDialog(msg)
                } else {
                    showAlertDialog("FCM message received is empty")
                }
            }
        }
    }
    private fun getRegToken() {
        messagingHelp.getRegToken()
    }

    private fun subscribeToTopic() {
        messagingHelp.subscribeToTopic()
    }


    //UI
    private fun updateUi(binding: MessagingActivityBinding, textUi: String) {
        binding.tvMessaging.text = textUi
    }

    private fun showAlertDialog(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setTitle("FirebaseDemo")
            .setPositiveButton("OK"){_, _ -> }
            .create()
            .show()
    }
}
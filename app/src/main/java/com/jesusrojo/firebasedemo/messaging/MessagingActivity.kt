package com.jesusrojo.firebasedemo.messaging


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.jesusrojo.firebasedemo.databinding.MessagingActivityBinding

class MessagingActivity : AppCompatActivity() {

    private lateinit var messagingHelp: MessagingHelp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // UI
        val binding = MessagingActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.subscribeButton.setOnClickListener {
            subscribeToTopic()
        }
        binding.logTokenButton.setOnClickListener {
            getRegToken()
        }

        // MESSAGING
        messagingHelp = MessagingHelp(this){ textUi ->
           binding.tvMessaging.text = textUi
        }
        messagingHelp.onCreate()
    }

    override fun onResume() {
        super.onResume()
        messagingHelp.onResume()
    }

    private fun getRegToken() {
        messagingHelp.getRegToken()
    }

    private fun subscribeToTopic() {
        messagingHelp.subscribeToTopic()
    }
}
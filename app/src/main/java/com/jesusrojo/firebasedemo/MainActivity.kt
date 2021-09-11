package com.jesusrojo.firebasedemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jesusrojo.firebasedemo.firebase.ui.FirebaseActivity
import com.jesusrojo.firebasedemo.firestore.FirestoreActivity
import com.jesusrojo.firebasedemo.messaging.MessagingActivity

class MainActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val btn01 = findViewById<Button>(R.id.btn_firebase)
        btn01.setOnClickListener {
            startActivity( Intent(this, FirebaseActivity::class.java))
        }

        val btn02 = findViewById<Button>(R.id.btn_firestore)
        btn02.setOnClickListener {
            startActivity(Intent(this, FirestoreActivity::class.java))
        }

        val btn03 = findViewById<Button>(R.id.btn_messaging)
        btn03.setOnClickListener {
            startActivity(Intent(this, MessagingActivity::class.java))
        }

        handleIntentMessagingOnCreate(this)
    }

    private fun handleIntentMessagingOnCreate(activity: Activity) {
        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        activity.intent.extras?.let {
            for (key in it.keySet()) {
                val value = activity.intent.extras?.get(key)
                val msg = "New MSG:\nKey: $key Value: $value"
                val tv: TextView = activity.findViewById(R.id.tv_main)
                tv.text = msg
            }
        }
    }
}
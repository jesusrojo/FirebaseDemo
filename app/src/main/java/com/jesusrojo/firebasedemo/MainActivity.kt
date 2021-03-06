package com.jesusrojo.firebasedemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jesusrojo.firebasedemo.firebase.ui.FirebaseActivity
import com.jesusrojo.firebasedemo.firestore.FirestoreActivity
import com.jesusrojo.firebasedemo.login.LoginActivity
import com.jesusrojo.firebasedemo.messaging.MessagingActivity
import com.jesusrojo.firebasedemo.messaging2.Messaging2Activity

class MainActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        initUi()
        handleIntentMessagingOnCreate(this)
    }

    private fun handleIntentMessagingOnCreate(activity: Activity) {
        activity.intent.extras?.let {
            for (key in it.keySet()) {
                val value = activity.intent.extras?.get(key)
                val msg = "New MSG:\nKey: $key Value: $value"
                val tv: TextView = activity.findViewById(R.id.tv_main)
                tv.text = msg
            }
        }
    }

    private fun initUi() {
        val btn01 = findViewById<Button>(R.id.btn_firebase)
        btn01.setOnClickListener {
            startActivity(Intent(this, FirebaseActivity::class.java))
        }

        val btn02 = findViewById<Button>(R.id.btn_firestore)
        btn02.setOnClickListener {
            startActivity(Intent(this, FirestoreActivity::class.java))
        }

        val btn03 = findViewById<Button>(R.id.btn_messaging)
        btn03.setOnClickListener {
            startActivity(Intent(this, MessagingActivity::class.java))
        }
        val btn04 = findViewById<Button>(R.id.btn_messaging2)
        btn04.setOnClickListener {
            startActivity(Intent(this, Messaging2Activity::class.java))
        }

        val btn05 = findViewById<Button>(R.id.btn_login)
        btn05.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
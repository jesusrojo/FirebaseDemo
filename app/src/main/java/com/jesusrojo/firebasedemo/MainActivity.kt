package com.jesusrojo.firebasedemo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.jesusrojo.firebasedemo.firebase.ui.FirebaseActivity
import com.jesusrojo.firebasedemo.firestore.FirestoreActivity

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
    }
}
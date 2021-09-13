package com.jesusrojo.firebasedemo.messaging2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jesusrojo.firebasedemo.R
import com.jesusrojo.firebasedemo.messaging2.ui.EggTimerFragment

class Messaging2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.mesaging2_activity_layout)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, EggTimerFragment.newInstance())
                .commitNow()
        }
    }
}
package com.jesusrojo.firebasedemo.messaging

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.GoogleApiAvailability
import com.jesusrojo.firebasedemo.R


class MessagingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.messanging_activity)

        checkForGooglePlayServices()
    }

    override fun onResume() {
        super.onResume()
        checkForGooglePlayServices()
    }

    // MESSAGING
    private fun checkForGooglePlayServices() {
        //https://firebase.google.com/docs/cloud-messaging/android/client#kotlin+ktx
        if (hasGooglePlayServices()) {
            TODO("Not yet implemented")
        } else {
            downloadGooglePlayServices()
        }
    }

    private fun hasGooglePlayServices(): Boolean {
        TODO("Not yet implemented")
        return true
    }

    private fun downloadGooglePlayServices() {
        GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this)
    }
}
package com.jesusrojo.firebasedemo.util

import com.google.android.gms.common.ConnectionResult

import com.google.android.gms.common.GoogleApiAvailability

import android.app.Activity


class GoogleUtil {

    companion object {

        fun isGooglePlayServicesAvailable(activity: Activity): Boolean {
            val googleApiAvailability = GoogleApiAvailability.getInstance()
            val status =
                googleApiAvailability.isGooglePlayServicesAvailable(activity.applicationContext)

            if (status != ConnectionResult.SUCCESS) {

                if (googleApiAvailability.isUserResolvableError(status)) {
                    val errorDialog = googleApiAvailability.getErrorDialog(activity, status, 2404)
                    errorDialog?.show()
                }
                return false
            }
            return true
        }

        fun downloadGooglePlayServices(activity: Activity) {
            GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(activity)
        }
    }
}
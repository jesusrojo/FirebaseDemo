package com.jesusrojo.firebasedemo.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

//https://firebase.google.com/docs/analytics/get-started?platform=android
class AnalyticsHelp {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    fun create() {

        firebaseAnalytics = Firebase.analytics // Obtain FirebaseAnalytics instance
    }

    fun logSomeEvents() {
        val id = 0
        val name = "name"
        val bundle = Bundle()


        //1use FirebaseAnalytics.Param.
        bundle.putInt(FirebaseAnalytics.Param.ITEM_ID, id)
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name)
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image")

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, bundle)

        //2 use our names
        bundle.putString("show_name", name)
        firebaseAnalytics?.logEvent("show_name", bundle)

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, bundle)
    }

}
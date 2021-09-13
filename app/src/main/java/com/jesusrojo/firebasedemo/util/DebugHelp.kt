package com.jesusrojo.firebasedemo.util

import android.util.Log


// _UP DEBUG
class DebugHelp {

    companion object{
        private const val jrTag = "##"
        var fullLog = ""

        fun l(classSimpleName: String, message: String) {
            fullLog += "\n$message"
            l("$classSimpleName $message $jrTag")
        }

        fun l(message: String) {
            fullLog += "\n$message"
           // Timber.d("$message $jrTag")
            Log.d (jrTag, message)
        }

        fun le(message: String) {
            fullLog += "\n$message"
          //  Timber.e("$message $jrTag")
            Log.d (jrTag, message)
        }

//        fun lt(message: String) {
//            fullLog += "\n$message"
//            Timber.d("$message THREAD: ${Thread.currentThread().name} $jrTag")
//        }
    }
}
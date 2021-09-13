package com.jesusrojo.firebasedemo.messaging

import android.content.Context
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters

class SendTokenWorker(
    private val context: Context,
    private  val workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {

        val token = workerParams.inputData.getString(TOKEN_KEY)
        val userId = workerParams.inputData.getString(USER_ID_KEY)

        return if(isNotNulNotEmpty(token) && isNotNulNotEmpty(userId)) {
            sendToServer(userId, token)
        } else {
            Toast.makeText(context, "error work manager", Toast.LENGTH_SHORT).show()
            Result.failure()
        }
    }

    private fun isNotNulNotEmpty(message: String?) = message != null && message.isNotEmpty()

    private fun sendToServer(userId: String?, token: String?): Result {

        //todo Retrofit localhost
        ///@POST("register/{userId}/{fcmToken}")
        return Result.success()
    }

    companion object {
        const val TOKEN_KEY  = "TOKEN_KEY"
        const val USER_ID_KEY  = "USER_ID_KEY"
    }
}
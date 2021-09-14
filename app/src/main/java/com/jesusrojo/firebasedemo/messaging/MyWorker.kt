package com.jesusrojo.firebasedemo.messaging

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.jesusrojo.firebasedemo.util.DebugHelp

class MyWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        DebugHelp.l(TAG, "Performing long running task in scheduled job")
        // TODO(developer): add long running task here.
        return Result.success()
    }

    companion object {
        private const val TAG = "MyWorker"
    }
}

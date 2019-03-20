package application.gdms.com.android_concepts

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MySampleService : Service() {

    private var TAG: String? = MySampleService::class.java.simpleName;

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate()")
    }

    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "onBind()")
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "" + Thread.currentThread().id)
        Log.d(TAG, "onStartCommand() started")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy()")
        super.onDestroy()

    }
}

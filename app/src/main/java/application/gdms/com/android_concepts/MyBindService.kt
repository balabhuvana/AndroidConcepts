package application.gdms.com.android_concepts

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import java.util.*

class MyBindService : Service() {

    private var TAG: String? = MyBindService::class.java.simpleName;
    var mRandomNumber: Int = 0;
    var mRandomNumberIsOn: Boolean = false
    private var localBinder: IBinder = MyLocalBinder()
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate()")
    }

    inner class MyLocalBinder : Binder() {
        fun getService(): MyBindService {
            return this@MyBindService
        }
    }

    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "onBind()")
        return localBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "" + Thread.currentThread().id)
        Log.d(TAG, "onStartCommand() started")
        mRandomNumberIsOn = true
        Thread(Runnable {
            startRandomNumberGenerator()
        }).start()
        return START_STICKY
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy()")
        super.onDestroy()

    }

    private fun startRandomNumberGenerator() {
        while (mRandomNumberIsOn) {
            Thread.sleep(1000)
            if (mRandomNumberIsOn) {
                mRandomNumber = Random().nextInt()
                Log.d(TAG, "Random number : " + mRandomNumber)
            }

        }
    }

    private fun stopRandomNumberGenerator() {
        mRandomNumberIsOn = false
    }

    fun getRandomNumber(): Int {
        return mRandomNumber
    }
}

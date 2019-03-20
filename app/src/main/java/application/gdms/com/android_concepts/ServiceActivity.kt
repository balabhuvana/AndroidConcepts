package application.gdms.com.android_concepts

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class ServiceActivity : AppCompatActivity() {

    var serviceIntent: Intent? = null
    var TAG: String? = ServiceActivity::class.java.simpleName
    var myBindService: MyBindService? = null
    var isServiceBound: Boolean = false
    var serviceConnection: ServiceConnection? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "" + Thread.currentThread().id)

        serviceIntent = Intent(applicationContext, MyBindService::class.java)

        btnStartService.setOnClickListener(View.OnClickListener {
            startService(serviceIntent)
        })

        btnStopService.setOnClickListener(View.OnClickListener {
            stopService(serviceIntent)
        })

        btnBoundService.setOnClickListener {
            if (serviceConnection == null) {
                serviceConnection = object : ServiceConnection {
                    override fun onServiceDisconnected(name: ComponentName?) {
                        isServiceBound = false
                    }

                    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                        val serviceBinder = service as MyBindService.MyLocalBinder
                        myBindService = serviceBinder.getService()
                        isServiceBound = true
                    }
                }
            }

            bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
        }

        btnUnBoundService.setOnClickListener {
            if (isServiceBound) {
                unbindService(serviceConnection)
                isServiceBound = false
            }
        }

        btnGetRandomNumber.setOnClickListener {
            setRandomNumber()
        }

    }

    fun setRandomNumber() {
        if (isServiceBound) {
            Log.d(TAG, "" + myBindService!!.getRandomNumber())
            textViewRandomNumber.setText("" + myBindService!!.getRandomNumber())
        } else {
            Toast.makeText(applicationContext, "Service is not bound", Toast.LENGTH_LONG).show()
        }
    }
}

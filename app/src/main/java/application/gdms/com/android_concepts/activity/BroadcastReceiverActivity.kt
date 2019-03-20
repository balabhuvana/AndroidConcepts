package application.gdms.com.android_concepts.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import application.gdms.com.android_concepts.R
import kotlinx.android.synthetic.main.activity_broadcast_receiver.*
import kotlinx.android.synthetic.main.content_broadcast_receiver.*

class BroadcastReceiverActivity : AppCompatActivity() {

    var customBroadcastReceiver: BroadcastReceiver? = null;
    var airplaneBroadcastReceiver: BroadcastReceiver? = null;
    var localBroadcastReceiver: BroadcastReceiver? = null;
    var intentFilter: IntentFilter? = null
    var localIntentFilter: IntentFilter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broadcast_receiver)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        airplaneBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Toast.makeText(applicationContext, "Airplane Mode", Toast.LENGTH_LONG).show()
            }
        }

        customBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val value = intent?.getStringExtra("key")
                value?.apply {
                    tvGetEventText.setText(value)
                }
            }
        }

        intentFilter = object : IntentFilter() {}
        intentFilter?.addAction(getPackageName() + "android.net.conn.CONNECTIVITY_CHANGE");

        btnSendBroadcast.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                var intent = Intent(getPackageName() + "android.net.conn.CONNECTIVITY_CHANGE")
                intent.putExtra("key", "Arun kumar")
                sendBroadcast(intent)
            }
        })

        btnAirplaneBroadcast.setOnClickListener {
            var intentAirplaneFilter = object : IntentFilter() {}
            intentAirplaneFilter?.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            registerReceiver(airplaneBroadcastReceiver, intentAirplaneFilter)
        }

        localIntentFilter = object : IntentFilter() {}
        localIntentFilter?.addAction(getPackageName() + "localBroadcastManager");

        localBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Toast.makeText(applicationContext, "Local Broadcast", Toast.LENGTH_LONG).show()
            }
        }

        btnLocalBroadcast.setOnClickListener {
            var localBroadcastManager = LocalBroadcastManager.getInstance(this)
            var intent = Intent(getPackageName() + "localBroadcastManager")
            localBroadcastManager.sendBroadcast(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(customBroadcastReceiver, intentFilter)
        registerReceiver(localBroadcastReceiver, localIntentFilter)
        LocalBroadcastManager.getInstance(this).registerReceiver(this!!.localBroadcastReceiver!!, this!!.localIntentFilter!!)
    }

    override fun onPause() {
        unregisterReceiver(customBroadcastReceiver)
        unregisterReceiver(airplaneBroadcastReceiver)
        LocalBroadcastManager.getInstance(this).registerReceiver(this!!.localBroadcastReceiver!!, this!!.localIntentFilter!!)
        super.onPause()
    }

}

package com.forcelain.android.rxserviceconnectionsample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class MainActivity : AppCompatActivity() {

    lateinit var connections: com.forcelain.android.rxservice.RxServiceConnection<MyService>

    private var subscription: rx.Subscription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById(R.id.one_more_activity)?.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        connections = com.forcelain.android.rxservice.RxServiceConnection()
        subscription = connections.bind(this, Intent(this, MyService::class.java))
                .flatMap { service -> service.interval() }
                .subscribe { Log.d("RxServiceConnection", "{${this@MainActivity.toString()}} interval says: $it"); }
    }

    override fun onDestroy() {
        super.onDestroy()
        subscription?.unsubscribe()
    }
}


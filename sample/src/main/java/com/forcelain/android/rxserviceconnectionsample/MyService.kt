package com.forcelain.android.rxserviceconnectionsample

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.util.concurrent.TimeUnit

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return MyBinder()
    }

    inner class MyBinder : Binder(), com.forcelain.android.rxservice.RxBinder<MyService> {
        override fun getService(): MyService = this@MyService
    }

    fun interval(): rx.Observable<Long> = rx.Observable.interval(1, TimeUnit.SECONDS)

}

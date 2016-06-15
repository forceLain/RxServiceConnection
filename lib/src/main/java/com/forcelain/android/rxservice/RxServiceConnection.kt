package com.forcelain.android.rxservice

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import rx.Observable
import rx.android.MainThreadSubscription

class RxServiceConnection<T : Service> {

    fun bind(context: Context, intent: Intent, flag: Int = Context.BIND_AUTO_CREATE): Observable<T> {
        return Observable.create<T> { subscriber ->
            val connection: ServiceConnection?
            var bound: Boolean = false
            connection = object : ServiceConnection {
                override fun onServiceDisconnected(name: ComponentName?) {
                }

                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    service as RxBinder<T>
                    if (!subscriber.isUnsubscribed) {
                        subscriber.onNext(service.getService())
                        subscriber.onCompleted()
                    }
                }
            }

            subscriber.add(object : MainThreadSubscription() {
                override fun onUnsubscribe() {
                    if (bound) {
                        context.unbindService(connection)
                    }
                }
            })

            bound = context.bindService(intent, connection, flag)
        }
    }
}
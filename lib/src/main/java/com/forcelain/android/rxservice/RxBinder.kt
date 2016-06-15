package com.forcelain.android.rxservice

import android.app.Service
import android.os.IBinder

interface RxBinder<T : Service> : IBinder {
    fun getService(): T;
}
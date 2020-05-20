package com.wiredcraft.androidtemplate.util

import android.os.Build

object DeviceInfoUtil {
    const val OS = "Android"
    fun getApiVersion(): Int = Build.VERSION.SDK_INT
    fun getSystemVersion(): String = Build.VERSION.RELEASE
    fun getDeviceModel(): String = Build.MODEL
}
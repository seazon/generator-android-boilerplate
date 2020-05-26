package <%= appPackage %>.plugin

import android.util.Log
import <%= appPackage %>.BuildConfig

interface LoggingPlugin {
    fun v(msg: Any?) {
        if (BuildConfig.DEBUG) {
            val tag = "${this::class.java.simpleName}"
            Log.v(tag, msg.toString())
        }
    }

    fun d(msg: Any?) {
        if (BuildConfig.DEBUG) {
            val tag = "${this::class.java.simpleName}"
            Log.d(tag, msg.toString())
        }
    }

    fun e(msg: Any?) {
        if (BuildConfig.DEBUG) {
            val tag = "${this::class.java.simpleName}"
            Log.e(tag, msg.toString())
        }
    }
}
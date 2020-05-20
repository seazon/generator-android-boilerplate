package com.wiredcraft.androidtemplate.ktx

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Point
import android.location.Location
import android.net.Uri
import android.util.TypedValue
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.wiredcraft.androidtemplate.R

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.toast(messageId: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, messageId, duration).show()
}


fun Context.showKeyboard(view: View) {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun Context.toggleKeyboard() {
    val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.hasSoftKey(): Boolean {
    val resourceId = resources.getIdentifier("config_showNavigationBar", "bool", "android")
    // REFER: http://stackoverflow.com/questions/16092431/check-for-navigation-bar/16608481#16608481
    val hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey()
    val hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME)
    val hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)

    return (resourceId > 0 && resources.getBoolean(resourceId)) || (!hasMenuKey && !hasBackKey)
}

val Context.navigationBarHeight: Int
    get() {
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
    }

fun Context.statusBarHeight(): Int {
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return resources.getDimensionPixelSize(resourceId)
}

fun Context.dp2px(dp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
}

fun Context.px2sp(px: Float): Float {
    return px / resources.displayMetrics.scaledDensity
}

fun Context.sp2px(sp: Float): Float {
    return sp * resources.displayMetrics.scaledDensity
}

fun Context.phoneIntent(number: String): Intent {
    return Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$number")
    }
}

inline fun Context.openExternalBrowserWith(url: String?, cb: () -> Unit = {}) {
    val i = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
    }
    try {
        startActivity(i, null)
    } catch (e: Exception) {
        cb.invoke()
    }
}

fun Context.copyToClipboard(label: String, text: String) {
    (getSystemService(CLIPBOARD_SERVICE) as ClipboardManager).run {
        val clip = ClipData.newPlainText(label, text)
        setPrimaryClip(clip)
    }
}

fun Context.windowManager(): WindowManager {
    return if (this is Activity) {
        windowManager
    } else {
        getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }
}

val Context.screenWidth: Int
    get() {
        val display = windowManager().defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.x
    }

val Context.screenHeight: Int
    get() {
        val display = windowManager().defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.y
    }

fun Context.isAppInstalled(id: String): Boolean {
    return try {
        packageManager.getApplicationInfo(id, 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}

fun Context.openAutonaviApp(
    originLoc: Location?,
    name: String,
    latitude: Double,
    longitude: Double
) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(
            "androidamap://route?sourceApplication=${getString(R.string.app_name)}"
                    + "&dlat=$latitude&dlon=$longitude&dname=$name"
                    + "&dev=0&m=0&t=4&showType=1"
        )
    }

    startActivity(intent)
}


fun Context.openBaiduMapApp(
    originLoc: Location?,
    name: String,
    city: String,
    latitude: Double,
    longitude: Double
) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        val mode = "driving"
        val origin = if (originLoc is Location)
            "origin=latlng:${originLoc.latitude},${originLoc.longitude}|name:&"
        else
            ""
        val destination = "destination=latlng:$latitude,$longitude|name:$name"
        data = Uri.parse(
            "baidumap://map/direction?$origin$destination&mode=$mode&region=$city&coord_type=wgs84&src=$packageName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end"
        )
    }

    startActivity(intent)
}


val Context.approximateSoftKeyBoardHeight: Int
    get() = screenHeight / 5 * 3

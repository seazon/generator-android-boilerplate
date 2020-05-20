package com.wiredcraft.androidtemplate.ui.view.main

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.navigation.NavOptions
import com.wiredcraft.androidtemplate.base.BaseActivity
import com.wiredcraft.androidtemplate.ui.custom.CoreMaterialDialog
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import net.yslibrary.android.keyboardvisibilityevent.Unregistrar
import javax.inject.Inject

abstract class CoreMainActivity : BaseActivity(), KeyboardVisibilityEventListener {
    @Inject
    lateinit var factory: Factory
    protected lateinit var mUnregistrar: Unregistrar
    var mCurrentDialog: Dialog? = null
    var mDialogFragment: DialogFragment? = null

    val mvm: MainViewModel by lazy {
        ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

    /**
     * If call this method in fragment, please use base fragments's showMd()
     */
    fun showMd(
        leastState: Lifecycle.State = Lifecycle.State.STARTED,
        func: CoreMaterialDialog.Builder.() -> Unit
    ): CoreMaterialDialog {
        return CoreMaterialDialog.Builder(this@CoreMainActivity).show(func, this, leastState)
            .apply {
                mCurrentDialog = this
            }
    }

    fun showMd(
        owner: LifecycleOwner,
        leastState: Lifecycle.State = Lifecycle.State.STARTED,
        func: CoreMaterialDialog.Builder.() -> Unit
    ): CoreMaterialDialog {
        return CoreMaterialDialog.Builder(this@CoreMainActivity).show(func, owner, leastState)
            .apply {
                mCurrentDialog = this
            }
    }


    fun buildMd(
        owner: LifecycleOwner,
        func: CoreMaterialDialog.Builder.() -> Unit
    ): CoreMaterialDialog {
        return CoreMaterialDialog.Builder(this@CoreMainActivity).build(func, owner)
    }

    protected fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable to detect notch
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }

        toggleFullScreen(true)
        super.onCreate(savedInstanceState)
    }

    override fun onVisibilityChanged(isOpen: Boolean) {

    }

    fun checkLocationPermissionGranted() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    fun openAppSettings() {
        startActivity(Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", application.applicationInfo.packageName, null)
        })
    }

    fun openWirelessSettings() {
        startActivity(Intent().apply {
            action = Settings.ACTION_WIRELESS_SETTINGS
        })
    }

    fun openSystemLocationSettings() {
        startActivity(Intent().apply {
            action = Settings.ACTION_LOCATION_SOURCE_SETTINGS
        })
    }

    /*
    Saved nest graph id in memory, a temporary solution mainly for login/enrollment nav graph
    */
    abstract var nestGraphId: Int
    abstract val currentDestinationId: Int

    var softKeyboardHeight: Int = -1
    var displayCutoutHeight: Int = 0


    /*
     */
    abstract fun closeSoftKeyboard()

    /*
     */
    abstract fun showGeneralErrorMd(owner: LifecycleOwner)
    abstract fun showTimeoutErrorMd(owner: LifecycleOwner)
    abstract fun showNetworkErrorMd(owner: LifecycleOwner)

    abstract fun goToLogin(options: NavOptions)

    abstract suspend fun goToHomeNavGraph()
    abstract fun popBackStackToHome(inclusive: Boolean)
    /*
     */
    var isLoading: Boolean = false
    abstract fun showLoading(modal: Boolean = false, title: String? = null)
    abstract fun dismissLoading()


    /*
     */
    abstract fun setWindowBackgroundDrawable(resId: Int)
    abstract fun setStatusBarColor(resId: Int)
    abstract fun toggleLightStatusBar(light: Boolean)
    abstract fun toggleFullScreen(toggle: Boolean)

    // dialog
    abstract fun registerDialog(dialog: Dialog)
}
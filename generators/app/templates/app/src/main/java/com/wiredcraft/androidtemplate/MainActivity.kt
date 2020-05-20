package com.wiredcraft.androidtemplate

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.wiredcraft.androidtemplate.ui.view.main.CoreMainActivity
import com.wiredcraft.androidtemplate.databinding.ActivityMainBinding
import com.wiredcraft.androidtemplate.ktx.hideKeyboard
import com.wiredcraft.androidtemplate.ktx.orZero
import com.wiredcraft.androidtemplate.plugin.LoggingPlugin
import com.wiredcraft.androidtemplate.ui.view.main.MainViewModel
import kotlinx.coroutines.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent

class MainActivity : CoreMainActivity(),
    OnApplyWindowInsetsListener,
    MainViewModel.ScreenChangeListener,
    NavController.OnDestinationChangedListener,
    LoggingPlugin {
    private var mNestGraphId: Int = R.id.navGraphHome
    private val mNavController by lazy { findNavController(R.id.navController) }

//    private val mGoToJob: Job = SupervisorJob()


    private lateinit var mBinding: ActivityMainBinding


    override var nestGraphId: Int
        get() = mNestGraphId
        set(value) {
            mNestGraphId = value
        }
    override val currentDestinationId: Int
        get() = mNavController.currentDestination?.id.orZero()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        ViewCompat.setOnApplyWindowInsetsListener(mBinding.root, this)
        mNavController.addOnDestinationChangedListener(mvm)
        mNavController.addOnDestinationChangedListener(this)
        mUnregistrar = KeyboardVisibilityEvent.registerEventListener(this, this)

        setUpViews()
        setUpObservers()
    }

    private fun setUpObservers() {

    }

    override fun onApplyWindowInsets(v: View?, insets: WindowInsetsCompat): WindowInsetsCompat {
        d("${insets.displayCutout}")
        displayCutoutHeight = insets.displayCutout?.boundingRects?.firstOrNull()?.height() ?: 0
        return insets
    }


    override fun showGeneralErrorMd(owner: LifecycleOwner) {
        showMd(owner) {
            content(R.string.error_unknown)
            positiveText(R.string.hh_OK)
            autoDismiss(true)
            canceledOnTouchOutside(false)
        }
    }

    override fun showTimeoutErrorMd(owner: LifecycleOwner) {
        showMd(owner) {
            content(R.string.error_timeout)
            positiveText(R.string.hh_OK)
            autoDismiss(true)
            canceledOnTouchOutside(false)
        }
    }

    override fun showNetworkErrorMd(owner: LifecycleOwner) {
        showMd(owner) {
            content(R.string.error_network)
            positiveText(R.string.hh_OK)
            autoDismiss(true)
            canceledOnTouchOutside(false)
        }
    }

    override fun closeSoftKeyboard() {
        hideKeyboard(currentFocus ?: mBinding.root)
    }

    private fun setUpViews() {
    }

    override fun onScreenChange(arguments: Bundle?) {
    }


    override fun goToLogin(options: NavOptions) {
        mNavController.navigate(R.id.loginScreen, null, options, null)
    }

    override suspend fun goToHomeNavGraph() = withContext(Dispatchers.Main) {
        mNavController.navigate(R.id.navGraphHome)
    }

    override fun popBackStackToHome(inclusive: Boolean) {
        mNavController.popBackStack(R.id.homeScreen, inclusive)
    }


    override fun showLoading(modal: Boolean, title: String?) {
        lifecycleScope.launch(Dispatchers.Main) {
            closeSoftKeyboard()

            if (mBinding.loading.visibility != View.VISIBLE)
                mBinding.loading.visibility = View.VISIBLE
        }
        isLoading = true
    }

    override fun dismissLoading() {
        lifecycleScope.launch(Dispatchers.Main) {
            if (mBinding.loading.visibility != View.GONE)
                mBinding.loading.visibility = View.GONE
        }
        isLoading = false
    }

    override fun setWindowBackgroundDrawable(resId: Int) {
        TODO("Not yet implemented")
    }


    override fun onDestroy() {
        super.onDestroy()
        mUnregistrar.unregister()
        mNavController.removeOnDestinationChangedListener(mvm)
        mNavController.removeOnDestinationChangedListener(this)
    }

    override fun navigateUpTo(upIntent: Intent?): Boolean {
        return mNavController.navigateUp() || super.navigateUpTo(upIntent)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        d("Destination id ${destination.id}")
        lifecycleScope.launch(Dispatchers.Main) {

            //
            closeSoftKeyboard()
            d("Going to assign nest nav graph to home")
            mNestGraphId = R.id.navGraphHome
            toggleLightStatusBar(false)
        }
    }

    override fun setStatusBarColor(resId: Int) {
        window.statusBarColor = ContextCompat.getColor(this, resId)
    }

    override fun toggleLightStatusBar(light: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility =
                if (light)
                    (window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                else
                    (window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv())
        }
    }

    override fun toggleFullScreen(toggle: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility =
                if (toggle)
                    (window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
                else
                    (window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN.inv())
        }
    }


    private fun dismissPopups() {
        mCurrentDialog?.dismiss()
        mDialogFragment?.dismiss()
    }

    override fun registerDialog(dialog: Dialog) {
        mCurrentDialog = dialog
    }


}

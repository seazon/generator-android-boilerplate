package com.wiredcraft.androidtemplate.base

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.wiredcraft.androidtemplate.ui.view.main.CoreMainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wiredcraft.androidtemplate.plugin.LoggingPlugin
import com.wiredcraft.androidtemplate.ui.custom.CoreMaterialDialog
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment(),
    HasAndroidInjector, LoggingPlugin {
    @Inject
    protected lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    protected lateinit var factory: ViewModelProvider.Factory


    protected val mainActivity by lazy { activity as CoreMainActivity }

    @Deprecated("Using ktx method findNavController() instead")
    protected val navController: NavController by lazy { NavHostFragment.findNavController(this) }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun androidInjector(): AndroidInjector<Any> =
        androidInjector

    override fun onDestroyView() {
        super.onDestroyView()
        System.gc()
    }

    fun showMd(
        leastState: Lifecycle.State = Lifecycle.State.RESUMED,
        func: CoreMaterialDialog.Builder.() -> Unit
    ): CoreMaterialDialog {
        return mainActivity.showMd(this, leastState, func)
    }
}
package <%= appPackage %>.base

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import <%= appPackage %>.ui.view.main.CoreMainActivity
import <%= appPackage %>.plugin.LoggingPlugin
import <%= appPackage %>.ui.custom.CoreMaterialDialog
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import javax.inject.Inject

abstract class BaseDialogFragment : DialogFragment(),
    HasAndroidInjector, CoroutineScope by MainScope(), LoggingPlugin {
    @Inject
    protected lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    protected lateinit var factory: ViewModelProvider.Factory

    protected val mainActivity by lazy { activity as CoreMainActivity }

    @Deprecated("Using ktx method findNavController() instead")
    protected val navController: NavController by lazy { NavHostFragment.findNavController(this) }

    fun show(manager: FragmentManager?) {
        manager?.let {
            show(it, this::class.java.simpleName)
        }
    }

    fun showAsSingleton(manager: FragmentManager?, tag: String) {
        manager?.run {
            if (findFragmentByTag(tag) !is Fragment) {
                show(this, tag)
            }
        }
    }

    fun showAsSingleton(manager: FragmentManager?) {
        manager?.run {
            if (findFragmentByTag(this::class.java.simpleName) !is Fragment) {
                show(this, this::class.java.simpleName)
            }
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setVmObserversOnCreate()
    }

    open fun setVmObserversOnCreate() {
    }

    override fun show(manager: FragmentManager, tag: String?) {
        manager.run {
            if (isStateSaved) return
            super.show(this, tag)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.run {
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
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
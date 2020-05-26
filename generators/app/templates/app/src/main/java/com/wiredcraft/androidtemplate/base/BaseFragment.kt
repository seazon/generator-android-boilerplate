package <%= appPackage %>.base

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import <%= appPackage %>.ui.view.main.CoreMainActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import <%= appPackage %>.ktx.NoArgsCallback
import <%= appPackage %>.plugin.LoggingPlugin
import <%= appPackage %>.ui.custom.CoreMaterialDialog
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.*
import javax.inject.Inject

abstract class BaseFragment : Fragment(), HasAndroidInjector, LoggingPlugin {
    @Inject
    protected lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    protected lateinit var factory: ViewModelProvider.Factory

    protected val mainActivity by lazy { requireActivity() as CoreMainActivity }

    protected var mIsKeyboardOpened = false
    private var mEnsureKeyboardClosedThenDoJob = SupervisorJob()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    protected fun withImgLoader(res: Any, func: RequestBuilder<Drawable>.() -> Unit) {
        Glide.with(this)
            .load(res)
            .func()
    }

    protected fun setSoftInputModeToResize() {
        requireActivity().window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )
    }

    protected fun setSoftInputMode(flag: Int) {
        requireActivity().window.setSoftInputMode(flag)
    }

    fun showMd(
        leastState: Lifecycle.State = Lifecycle.State.RESUMED,
        func: CoreMaterialDialog.Builder.() -> Unit
    ): CoreMaterialDialog {
        return mainActivity.showMd(this, leastState, func)
    }

    fun ensureKeyboardClosedThenDo(waiting: Long = 300, action: NoArgsCallback) {
        mEnsureKeyboardClosedThenDoJob.cancelChildren()
        if (mIsKeyboardOpened) {
            lifecycleScope.launch(mEnsureKeyboardClosedThenDoJob) {
                mainActivity.closeSoftKeyboard()
                delay(waiting)
                action.invoke()
            }
        } else {
            action.invoke()
        }
    }
}
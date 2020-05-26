package <%= appPackage %>.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import kotlinx.coroutines.launch
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import net.yslibrary.android.keyboardvisibilityevent.Unregistrar

abstract class BaseScreenFragment : BaseFragment(), KeyboardVisibilityEventListener {

    private lateinit var mUnregistrar: Unregistrar
    protected lateinit var navController: NavController


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = NavHostFragment.findNavController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleScope.launch { interceptDeepLinkOnCreate() }
        super.onCreate(savedInstanceState)
        setVmObserversOnCreate()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainActivity.dismissLoading()
        mUnregistrar = KeyboardVisibilityEvent.registerEventListener(requireActivity(), this)

        super.onViewCreated(view, savedInstanceState)
        setVmObserversOnViewCreated()
    }


    override fun onDestroyView() {
        mUnregistrar.unregister()
        super.onDestroyView()
        System.gc()
    }

    override fun onVisibilityChanged(isOpen: Boolean) {
        mIsKeyboardOpened = isOpen
        lifecycleScope.launch {
            if (isOpen) {
                expandOnKeyboardOpening()
            } else {
                collapseOnKeyboardClosing()
            }
        }
    }

    protected open fun setVmObserversOnCreate() {}
    protected open fun setVmObserversOnViewCreated() {}
    protected open suspend fun expandOnKeyboardOpening() {}
    protected open suspend fun collapseOnKeyboardClosing() {}
    protected open suspend fun interceptDeepLinkOnCreate() {}

}
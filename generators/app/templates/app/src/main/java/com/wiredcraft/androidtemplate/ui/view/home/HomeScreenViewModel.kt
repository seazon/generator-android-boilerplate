package <%= appPackage %>.ui.view.home

import <%= appPackage %>.base.BaseScreenViewModel
import javax.inject.Inject

class HomeScreenViewModel @Inject constructor(

) : BaseScreenViewModel() {
    interface Navigator {
        fun goToLogin()
    }

    private lateinit var mNavigator: Navigator

    fun setNavigator(navigator: Navigator) {
        mNavigator = navigator
    }

    fun onLoginClicked() {
        mNavigator.goToLogin()
    }

}

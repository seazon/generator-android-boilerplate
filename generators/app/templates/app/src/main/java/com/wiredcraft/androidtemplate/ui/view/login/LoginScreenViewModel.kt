package <%= appPackage %>.ui.view.login

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import <%= appPackage %>.api.ApiManager
import <%= appPackage %>.base.BaseScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

class LoginScreenViewModel @Inject constructor(
    private val mApiManager: ApiManager
) :
    BaseScreenViewModel() {
    interface LoginScreenNavigator {
        fun goUp()
    }

    private lateinit var mNavigator: LoginScreenNavigator

    val username: MutableLiveData<String> = MutableLiveData()
    val state: MutableLiveData<String> = MutableLiveData("login")

    fun setNavigator(navigator: LoginScreenNavigator) {
        mNavigator = navigator
    }

    fun onUpClicked() = mNavigator.goUp()

    @VisibleForTesting
    suspend fun login() {
        try {
            if (username.value.isNullOrBlank()) {
                state.postValue("no username")
            } else {
                mApiManager.logIn(username.value.orEmpty()).let { res ->
                    if (res.isSuccessful) {
                        state.postValue("login success with ${username.value}")
                    } else {
                        state.postValue("login fail with ${username.value}: $res")
                    }
                }
            }
        } catch (e: HttpException) {
            state.postValue("login fail ${e.message}")
        } catch (e: Exception) {
            state.postValue("login fail ${e.message}")
        } finally {
            mIsLoading.postValue(false)
        }
    }

    fun onSubmitClicked() {
        mIsLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            login()
        }
    }
}
